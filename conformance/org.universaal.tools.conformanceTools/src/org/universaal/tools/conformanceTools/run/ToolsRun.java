package org.universaal.tools.conformanceTools.run;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;
import org.universaal.tools.conformanceTools.Activator;
import org.universaal.tools.conformanceTools.checks.api.CheckImpl;
import org.universaal.tools.conformanceTools.checks.impl.ActivatorCheck;
import org.universaal.tools.conformanceTools.checks.impl.Maven_nature;
import org.universaal.tools.conformanceTools.checks.impl.NameGroupID;
import org.universaal.tools.conformanceTools.checks.impl.OSGI_bundle;
import org.universaal.tools.conformanceTools.checks.impl.POM_file;
import org.universaal.tools.conformanceTools.markers.CTMarker;
import org.universaal.tools.conformanceTools.markers.Markers;
import org.universaal.tools.conformanceTools.utils.HtmlPage;
import org.universaal.tools.conformanceTools.utils.HtmlPage.Table;
import org.universaal.tools.conformanceTools.utils.RunPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ToolsRun {

	private static ToolsRun instance;

	private IProject projectToAnalyze;
	private IWorkbenchWindow window;
	private ISelection selection;

	private int bugIdCounter = -1;
	private ArrayList<BugDescriptor> bugsMap;
	private ArrayList<BugDescriptor> orderderbugsMap;

	private CTMarker markers;

	private final String fileNameResults = "uAAL_CTanalysisResult.html";
	private final String no_severity = "HINT";
	private final String low_severity = "INFO";
	private final String normal_severity = "WARNING";
	private final String high_severity = "ERROR";

	private final int lineNotPresent = -1;	

	private ToolsRun(){
		orderderbugsMap = new ArrayList<BugDescriptor>();
		markers = Markers.getInstance();		
	}

	public static synchronized ToolsRun getInstance(){
		if(instance == null)
			instance = new ToolsRun();

		return instance;
	}

	public void run(IWorkbenchWindow window, RunPlugin plugin) {

		this.window = window;

		this.selection = window.getSelectionService().getSelection("org.eclipse.jdt.ui.PackageExplorer");
		if(this.selection == null){
			this.selection = window.getSelectionService().getSelection("org.eclipse.ui.navigator.ProjectExplorer");
			System.out.println("uAAL CT - using selection from Project Explorer.");
		}
		else
			System.out.println("uAAL CT - using selection from Package Explorer.");

		if ((selection != null) && (selection instanceof StructuredSelection)) {

			Object selected = ((StructuredSelection) selection).getFirstElement();

			if (selected instanceof JavaProject)
				this.projectToAnalyze = ((JavaProject) selected).getProject();			
			else if (selected instanceof IProject)
				this.projectToAnalyze = ((IProject) selected);			
			else {
				MessageDialog.openInformation(window.getShell(),
						"uAAL Conformance Tools", "the selection is not a project or is broken.");

				return;
			}
			try{
				verifyImages();

				if(plugin == RunPlugin.CodeStyle){
					removeOldBugs();
					testConformance();
				}

				if(plugin == RunPlugin.CustomChecks){

					List<String> checks = new ArrayList<String>();
					checks.add(ActivatorCheck.class.getName());
					checks.add(Maven_nature.class.getName());
					checks.add(NameGroupID.class.getName());
					checks.add(OSGI_bundle.class.getName());
					checks.add(POM_file.class.getName());				

					List<Result> results = test(checks);
					visualizeNewResults(results);
				}
			}
			catch(Exception ex){ ex.printStackTrace(); }
		}
		else
			System.out.println("uAAL CT - no valid selection.");
	}

	private void visualizeNewResults(List<Result> results){

		try{
			HtmlPage page = new HtmlPage("uAAL CONFORMANCE TOOLS - ANALYSIS RESULTS");
			String path_ = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/images/logos/";

			Table t = page.new Table(results.size()+2, 4);

			// table header
			t.addContentCentered("<font size='5'><b>TEST RESULT</b></font>", 0, 0);
			t.addContentCentered("<font size='5'><b>TEST NAME</b></font>", 0, 1);
			t.addContentCentered("<font size='5'><b>TEST DESCRIPTION</b></font>", 0, 2);
			t.addContentCentered("<font size='5'><b>RESULT DESCRIPTION</b></font>", 0, 3);

			for(int i = 0; i < results.size(); i++){
				Result check = results.get(i);
				t.addContentCentered("<img src='"+path_+check.resultImg+"' />", i+1, 0);		
				t.addContentCentered(check.checkName, i+1, 1);			
				t.addContentCentered(check.checkDescription, i+1, 2);
				t.addContentCentered(check.resultDscr, i+1, 3);
			}

			page.getBody().addElement(t.getTable());
			page.getBody().addElement("<br/><br/><p>Total: "+results.size()+" performed test(s).</p>");
			String filePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/"+fileNameResults;
			File file = new File(filePath);
			page.write(file);

			IDE.openEditorOnFileStore( window.getActivePage(), EFS.getLocalFileSystem().getStore(file.toURI()) );
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private List<Result> test(List<String> checks){

		List<Result> results = new ArrayList<Result>();
		try{
			for(String c: checks){
				Object ck = Class.forName(c).newInstance();
				CheckImpl check = (CheckImpl) ck;

				String name = check.getCheckName();
				String description = check.getCheckDescription();

				String resultImg = check.check(this.projectToAnalyze);
				String resultDscr = check.getCheckResultDescription();

				results.add(new Result(name, description, resultImg, resultDscr));
			}

			if(results != null)
				return results;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return null;
	}

	private class Result{

		private String checkName, checkDescription, resultImg, resultDscr; 

		public Result(String checkName, String checkDescription, String resultImg, String resultDscr){
			this.checkName = checkName;
			this.checkDescription = checkDescription;
			this.resultImg = resultImg;
			this.resultDscr = resultDscr;
		}
	}

	private void testConformance() throws CoreException {

		final String path_ = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName();

		Job job = new Job("AAL Studio") {
			protected IStatus run(IProgressMonitor monitor) {

				try{					
					// Continuous progress bar
					monitor.beginTask("uAAL CT - verifying conformance", IProgressMonitor.UNKNOWN);

					IMavenProjectRegistry projectManager = MavenPlugin.getMavenProjectRegistry();

					if (projectToAnalyze != null && !projectToAnalyze.hasNature(IMavenConstants.NATURE_ID)) {
						monitor.done();
						return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT - not a Maven project.");
					}

					IFile pomResource = projectToAnalyze.getFile(IMavenConstants.POM_FILE_NAME);
					if (pomResource == null) {
						monitor.done();
						return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT - missing POM file.");
					}
					IMavenProjectFacade projectFacade = projectManager.create(pomResource, true, monitor);

					IMaven maven = MavenPlugin.getMaven();
					if(pomResource != null && projectFacade != null){
						MavenExecutionRequest request = projectManager.createExecutionRequest(pomResource, projectFacade.getResolverConfiguration(), monitor);

						List<String> goals = new ArrayList<String>();

						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						IWorkspaceDescription description = workspace.getDescription();
						if (!description.isAutoBuilding())
							goals.add("compiler:compile"); // compile it if autobuilding is off

						//if(plugin == RunPlugin.FindBugs)
						goals.add("findbugs:findbugs");

						//else if(plugin == RunPlugin.CheckStyle)
						goals.add("checkstyle:checkstyle");

						request.setGoals(goals);
						MavenExecutionResult result = maven.execute(request, monitor);

						if(result.hasExceptions()) {
							String errors = "Results: \n";
							for(Throwable e : result.getExceptions()) {

								if(e.getCause() != null && e.getCause().getMessage() != null)
									errors += e.getCause().getMessage();
							}

							monitor.done();
							System.out.println("uAAL CT - report blocked - "+errors);
							//if(errors.contains("java.lang.OutOfMemoryError"))
							//System.out.println("uAAL CT: verify start parameter [-XX:MaxPermSize] of AAL Studio and increase the value set.");

							return new Status(Status.ERROR, Activator.PLUGIN_ID, errors);
						}
						else{
							monitor.done(); // test monitor

							// generate report and visualize it
							monitor.beginTask("uAAL CT - reporting conformance tests.", IProgressMonitor.UNKNOWN);

							goals.clear();
							MavenExecutionRequest request2 = projectManager.createExecutionRequest(pomResource,
									projectFacade.getResolverConfiguration(), monitor);

							Properties props = new Properties();
							//if(plugin == RunPlugin.FindBugs){

							goals.add("findbugs:check");
							props.setProperty("findbugs.failOnError", "false");
							//}
							//else if(plugin == RunPlugin.CheckStyle){

							goals.add("checkstyle:check");
							props.setProperty("checkstyle.failOnViolation", "false");
							props.setProperty("checkstyle.failsOnError", "false");
							//}

							request2.setUserProperties(props);
							request2.setGoals(goals);
							MavenExecutionResult result2 = maven.execute(request2, monitor);

							if(result2.hasExceptions()) {
								String errors = "Results: \n";
								for(Throwable e : result2.getExceptions()) {

									if(e.getCause() != null && e.getCause().getMessage() != null)
										errors += e.getCause().getMessage();
								}

								monitor.done();
								return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT - "+errors);
							}

							// visualize report - open report file
							window.getWorkbench().getDisplay().syncExec(new Runnable() {

								@Override
								public void run() {

									try{									
										//if(plugin == RunPlugin.CheckStyle)
										//f = new File(path_+"/target/checkstyle-result.xml"); 
										int[] maxMinCK = parseCheckstyleResult(new File(path_+"/target/checkstyle-result.xml"));

										//if(plugin == RunPlugin.FindBugs)
										//f = new File(path_+"/target/findbugsXml.xml"); 
										int[] maxMinFB = parseFindBugsResults(new File(path_+"/target/findbugsXml.xml"));

										int max = 0, min = 0;
										if(maxMinCK[0] >= maxMinFB[0])
											max = maxMinCK[0];
										else
											max = maxMinFB[0];
										if(maxMinCK[1] < maxMinFB[1])
											min = maxMinCK[1];
										else
											min = maxMinFB[1];

										orderBySeverity(max, min);

										//if(plugin == RunPlugin.FindBugs) 
										//parseFindBugsResults(f);
										//else if(plugin == RunPlugin.CheckStyle)
										//parseCheckstyleResult(f);

										visualizeResults();
										//f = new File(path_+"/target/site/"+fileNameResults);

										org.eclipse.core.filesystem.IFileStore fileStore = EFS.getLocalFileSystem().getStore(
												new File(path_+"/target/site/"+fileNameResults).toURI());

										try {
											if(fileStore != null)
												IDE.openEditorOnFileStore( window.getActivePage(), fileStore );
											else
												System.out.println("uAAL CT - can't open report file.");
										} 
										catch ( PartInitException e ) {
											e.printStackTrace();
										}
									}
									catch(Exception ex){
										ex.printStackTrace();
									}
								}
							});
						}
					}
					else
						return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT - something went wrong.");
				}
				catch(Exception ex){
					ex.printStackTrace();
					monitor.done();
					return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT - save console log and e-mail it for debugging purpose.");
				}

				monitor.done();
				return new Status(Status.OK, Activator.PLUGIN_ID, "uAAL CT - good!");
			}
		};

		job.setUser(true);
		job.schedule();	
	}

	private int[] parseFindBugsResults(File xml){

		int maxSeverity = -1;
		int minSeverity = 1000;
		try{
			if(xml != null){
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document results = docBuilder.parse(xml);

				NodeList bugs = results.getElementsByTagName("BugInstance");				
				for(int i = 0; i < bugs.getLength(); i++){
					Node bug = bugs.item(i);

					BugDescriptor bd = new BugDescriptor();

					//bd.setPlugin(RunPlugin.FindBugs);

					if(bug.getNodeType() == Node.ELEMENT_NODE){
						Element bug_ = (Element) bug;

						try{
							bd.setLine(Integer.parseInt(getNodesContent(bug_.getChildNodes(), "SourceLine", "start").get(0)));
						}
						catch(Exception ex){
							bd.setLine(-1);
						}
						bd.setClazz(getNodesContent(bug_.getChildNodes(), "SourceLine", "sourcepath").get(0));
						bd.setDescr(getNodesContent(bug_.getChildNodes(), "LongMessage", null).get(0));
						bd.setErrorType(bug_.getAttribute("type").toLowerCase());
						try{
							bd.setSeverity(Integer.parseInt(bug_.getAttribute("rank"))); 
						}
						catch(Exception ex){
							bd.setSeverity(lineNotPresent);
						}
						bd.setSeverityDescription(getSeverityDescription(bd.getSeverity()));			

						if(bd.getSeverity() > maxSeverity)
							maxSeverity = bd.getSeverity();
						if(bd.getSeverity() < minSeverity)
							minSeverity = bd.getSeverity();

						bugsMap.add(bd);
					}
				}
			}

			//orderBySeverity(maxSeverity, minSeverity);
			markClasses();
			return new int[]{maxSeverity, minSeverity};
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return new int[]{0, 0};
	}

	private int[] parseCheckstyleResult(File xml){

		int min = 0, max = 0;
		try{
			if(xml != null){
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document results = docBuilder.parse(xml);

				NodeList files = results.getElementsByTagName("file");		
				for(int i = 0; i < files.getLength(); i++){
					Node file = files.item(i); 
					if(file.getNodeType() == Node.ELEMENT_NODE){
						Element file_ = (Element) file;			
						String fileName = file_.getAttribute("name");

						for(int j = 0; j < file_.getElementsByTagName("error").getLength(); j++){
							Node error = file_.getElementsByTagName("error").item(j);
							if(error.getNodeType() == Node.ELEMENT_NODE){
								Element error_ = (Element) error;

								BugDescriptor bd = new BugDescriptor();
								//bd.setPlugin(RunPlugin.CheckStyle);

								String separator = "\\\\";
								String[] path = fileName.trim().split(separator);
								bd.setClazz(path[path.length-1]);

								try{
									bd.setLine(Integer.parseInt(error_.getAttribute("line"))); 
								}
								catch(Exception ex){
									bd.setLine(lineNotPresent);
								}
								String severity = error_.getAttribute("severity"); //severity -> number
								if(severity.equalsIgnoreCase("ignore")){
									bd.setSeverity(0); min = 0; //3
								}
								else if(severity.equalsIgnoreCase("info"))
									bd.setSeverity(3); //8
								else if(severity.equalsIgnoreCase("warning"))
									bd.setSeverity(8); //13
								else if(severity.equalsIgnoreCase("error")){
									bd.setSeverity(13); max = 13; // 19
								}
								bd.setSeverityDescription(getSeverityDescription(bd.getSeverity()));

								bd.setDescr(error_.getAttribute("message"));
								String[] type = error_.getAttribute("source").trim().split("\\."); //errorType
								bd.setErrorType(type[type.length - 1]); 

								bugsMap.add(bd);
							}
						}
					}
				}
			}

			//orderBySeverity(max, min);
			markClasses();
			return new int[]{max, min};
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return new int[]{0, 0};
	}

	private void removeOldBugs(){

		try{
			bugsMap = new ArrayList<BugDescriptor>();

			int k = 0; 
			for(int i = 0; i < orderderbugsMap.size(); i++){
				if(orderderbugsMap.get(i) != null)
					//if(orderderbugsMap.get(i).getPlugin() == this.plugin){
					orderderbugsMap.set(i, null);
				k++;
				//}
			}
			System.out.println("uAAL CT - deleted "+k+" bug instances.");

			markers.deleteAll(/*this.plugin*/);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void orderBySeverity(int max, int min){

		try{
			int k = max;

			while(k >= min){
				for(int i = 0; i < bugsMap.size(); i++){

					BugDescriptor error = bugsMap.get(i);					

					if(error.getSeverity() == k){
						error.setFrontEndID(++bugIdCounter);
						orderderbugsMap.add(error);
					}									
				}

				k--;
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void markClasses(){

		try {
			ArrayList<ICompilationUnit> cus = getAllClasses();
			for(int i = 0; i < cus.size(); i++){
				for(int j = 0; j < orderderbugsMap.size(); j++){
					if(orderderbugsMap.get(j) != null && orderderbugsMap.get(j).getMarkerID() == -1)
						if(cus.get(i).getCorrespondingResource().getName().equalsIgnoreCase(orderderbugsMap.get(j).getClazz())){
							orderderbugsMap.get(j).setCu(cus.get(i));				

							Map<String, Object> attributes = new HashMap<String, Object>();
							attributes.put(IMarker.LINE_NUMBER, orderderbugsMap.get(j).getLine());
							attributes.put(IMarker.MESSAGE, orderderbugsMap.get(j).getDescr());
							attributes.put(IMarker.LOCATION, orderderbugsMap.get(j).getFrontEndID());
							attributes.put(IMarker.LINE_NUMBER, orderderbugsMap.get(j).getLine()); 
							attributes.put(IMarker.TEXT, orderderbugsMap.get(j).getErrorType());

							if(!orderderbugsMap.get(j).getSeverityDescription().equals(no_severity)){

								//attributes.put(IMarker.SOURCE_ID, orderderbugsMap.get(j).getPlugin());

								if(orderderbugsMap.get(j).getSeverityDescription().equals(low_severity)){
									attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
									attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_LOW);
								}
								else if(orderderbugsMap.get(j).getSeverityDescription().equals(normal_severity)){
									attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
									attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);
								}
								else if(orderderbugsMap.get(j).getSeverityDescription().equals(high_severity)){
									attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);			
									attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
								}
								orderderbugsMap.get(j).setMarkerID(markers.createMarker(this.projectToAnalyze, cus.get(i).getCorrespondingResource(), attributes, false));
							}
						}
				}
			}

		} catch (CoreException e1) {
			e1.printStackTrace();
		}
	}

	private List<String> getNodesContent(NodeList elements, String nodeName, String attribute){

		List<String> result = new ArrayList<String>();
		if(elements != null && nodeName != null){
			for(int i = 0; i < elements.getLength(); i++){
				Node node = elements.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element node_ = (Element) node;
					if(node_.getNodeName().equalsIgnoreCase(nodeName))
						if(attribute != null)
							result.add(node_.getAttribute(attribute));
						else
							result.add(node_.getTextContent());
				}
			}
		}

		return result;
	}

	private void visualizeResults(){

		try{
			HtmlPage page = new HtmlPage("uAAL CONFORMANCE TOOLS - ANALYSIS RESULTS");
			String path_ = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/images/logos/maven-feather.png";
			page.getBody().addElement("<img src='"+path_+"' alt='Maven Logo' /><br/><br/>");

			Table t = page.new Table(getBugsNumber()+2, 6);

			// table header
			t.addContentCentered("<font size='5'><b>MARK NUMBER</b></font>", 0, 0);
			t.addContentCentered("<font size='5'><b>ERROR TYPE</b></font>", 0, 1);
			t.addContentCentered("<font size='5'><b>SEVERITY</b></font>", 0, 2);
			t.addContentCentered("<font size='5'><b>CLASS</b></font>", 0, 3);
			t.addContentCentered("<font size='5'><b>DESCRIPTION</b></font>", 0, 4);
			t.addContentCentered("<font size='5'><b>LINE</b></font>", 0, 5);

			int j = 0, mark = 0;
			for(int i = 0; i < orderderbugsMap.size(); i++){
				if(orderderbugsMap.get(i) != null){					
					t.addContentCentered(++mark+"", mark, j);			
					t.addContentCentered(orderderbugsMap.get(i).getErrorType(), mark, ++j);
					t.addContentCentered(orderderbugsMap.get(i).getSeverityDescription(), mark, ++j);
					t.addContentCentered(orderderbugsMap.get(i).getClazz(), mark, ++j);
					t.addContentCentered(orderderbugsMap.get(i).getDescr(), mark, ++j);
					if(orderderbugsMap.get(i).getLine() != lineNotPresent)
						t.addContentCentered(orderderbugsMap.get(i).getLine()+"", mark, ++j);
					else
						t.addContentCentered("n.a.", mark, ++j);
					j = 0;
				}
			}

			page.getBody().addElement(t.getTable());
			page.getBody().addElement("<br/><br/><p>Total: "+getBugsNumber()+" marks.</p>");
			String path = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/"+fileNameResults;
			page.write(new File(path));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private int getBugsNumber(){
		int j = 0;

		for(int i = 0; i < orderderbugsMap.size(); i++) 
			if(orderderbugsMap.get(i) != null)
				j++;

		return j;
	}

	private void verifyImages(){

		try{	
			String destPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+this.projectToAnalyze.getDescription().getName();
			String destInternalProjectPath = "/target/site/images/";

			Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);

			File dir = new File(Activator.absolutePath+"/icons/");
			File[] icons = dir.listFiles();

			File target, site, images, logos; // subdirectories structure
			target = new File(destPath+"/target");
			if(!target.exists())
				target.mkdir();
			site = new File(destPath+"/target/site");
			if(!site.exists())
				target.mkdir();
			images = new File(destPath+"/target/site/images");
			if(!images.exists())
				images.mkdir();
			logos = new File(destPath+"/target/site/images/logos/");
			if(!logos.exists())
				logos.mkdir();			

			for(File icon: icons){
				copyFile(bundle, destPath+destInternalProjectPath, icon.getName(), "/icons/");
				copyFile(bundle, destPath+destInternalProjectPath+"logos/", icon.getName(), "/icons/");
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void copyFile(Bundle bundle, String destPath, String fileName, String sourcePath){

		try{
			Path path = new Path(sourcePath+fileName);
			URL fileURL = Platform.find(bundle, path);
			InputStream is = fileURL.openStream(); 			
			OutputStream os = new FileOutputStream(destPath+fileName);
			byte[] buffer = new byte[4096];  
			int bytesRead;  
			while ((bytesRead = is.read(buffer)) != -1) {  
				os.write(buffer, 0, bytesRead);  
			}  
			is.close();  
			os.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private ArrayList<ICompilationUnit> getAllClasses() throws JavaModelException {

		ArrayList<ICompilationUnit> classList = new ArrayList<ICompilationUnit>();

		IJavaProject javaProject = JavaCore.create(this.projectToAnalyze);
		IPackageFragment[] packages = javaProject.getPackageFragments();

		for (IPackageFragment myPackage : packages) {
			ICompilationUnit[] cus = myPackage.getCompilationUnits();
			for(ICompilationUnit cu : cus){
				classList.add(cu);
			}
		}		

		return classList;
	}

	private String getSeverityDescription(int sev){

		if(sev < 4)
			return no_severity;
		else if(sev < 9)
			return low_severity;
		else if(sev < 14)
			return normal_severity;
		else if(sev < 20)
			return high_severity;

		return no_severity;
	}

	private class BugDescriptor{

		private int frontEndID;
		private int markerID = -1;
		private int severity;
		private String severityDescription;
		private int line;
		private String descr;
		private String clazz;
		private String errorType;
		private ICompilationUnit cu;
		//private RunPlugin plugin;

		/*public RunPlugin getPlugin() {
			return plugin;
		}
		public void setPlugin(RunPlugin plugin) {
			this.plugin = plugin;
		}*/
		public ICompilationUnit getCu() {
			return cu;
		}
		public String getSeverityDescription() {
			return severityDescription;
		}
		public void setSeverityDescription(String severityDescription) {
			this.severityDescription = severityDescription;
		}
		public void setCu(ICompilationUnit cu) {
			this.cu = cu;
		}
		public int getSeverity() {
			return severity;
		}
		public void setSeverity(int severity) {
			this.severity = severity;
		}
		public int getLine() {
			return line;
		}
		public void setLine(int line) {
			this.line = line;
		}
		public String getDescr() {
			return descr;
		}
		public void setDescr(String descr) {
			this.descr = descr;
		}
		public String getClazz() {
			return clazz;
		}
		public void setClazz(String clazz) {
			this.clazz = clazz;
		}
		public String getErrorType() {
			return errorType;
		}
		public void setErrorType(String errorType) {
			this.errorType = errorType;
		}
		public int getMarkerID() {
			return markerID;
		}
		public void setMarkerID(int markerID) {
			this.markerID = markerID;
		}
		public int getFrontEndID() {
			return frontEndID;
		}
		public void setFrontEndID(int frontEndID) {
			this.frontEndID = frontEndID;
		}		
	}
}