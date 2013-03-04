package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

import org.universaal.tools.packaging.api.Page;

public class ContainerUnit {

	private Container container;

	private Embedding embedding;
	private List<KarafFeature> features;
	//private KarafFeature feature;

	private Android androidPart;

	public ContainerUnit(Embedding embedding, KarafFeature feature){
		this.container = Container.KARAF;
		this.embedding = embedding;
		getFeatures().add(feature);

		this.androidPart = null;
	}

	public ContainerUnit(Android androidPart){
		this.container = Container.ANDROID;
		this.androidPart= androidPart;

		this.embedding = null;
		this.features = null;
	}

	public ContainerUnit(Container container){
		if(container != Container.KARAF && container != Container.ANDROID)
			this.container = container;
		else
			throw new IllegalArgumentException("Please consider using proper constructor if container is Karaf or Android!");

		this.androidPart = null;
		this.embedding = null;
		this.features = null;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public Embedding getEmbedding() {
		return embedding;
	}

	public List<KarafFeature> getFeatures() {
		if(features == null)
			features = new ArrayList<KarafFeature>();
		return features;
	}

	public Android getAndroidPart() {
		return androidPart;
	}

	public void setAndroidPart(Android androidPart) {
		this.androidPart = androidPart;
	}

	public String getXML(){

		String r = "";

		if(container != Container.KARAF && container != Container.ANDROID)
			return "<"+container.toString()+"/>";
		else{
			if(container == Container.KARAF){
				r = r.concat("<karaf>");
				r = r.concat("<embedding>"+embedding.toString()+"</embedding>");

				//				Marshaller marshaller = null;
				//				try {
				//					marshaller = JAXBContext.newInstance(ObjectFactory.class).createMarshaller();
				//				} catch (Exception e) {
				//					e.printStackTrace();
				//				}
				//				StringWriter writer = new StringWriter();
				//
				//				JAXBElement p = new JAXBElement<FeaturesRoot>(
				//						new QName(
				//								"http://karaf.apache.org/xmlns/features/v1.0.0",
				//								"features"), FeaturesRoot.class, features);
				//
				//				try {
				//					marshaller.marshal(p, writer);
				//					r = r.concat(writer.getBuffer().toString()); // karaf features / repositories
				//				} catch (Exception e) {
				//					e.printStackTrace();
				//				}	

				r = r.concat("<"+Page.KARAF_NAMESPACE+":features>");
				for(int i = 0; i < features.size(); i++){

					KarafFeature ft = features.get(i);

					r = r.concat("<"+Page.KARAF_NAMESPACE+":feature name='"+ft.getName()+"' version='"+ft.getVersion()+"' description='"+ft.getDescription()+"' resolver='"+ft.getResolver()+"'>");
					r = r.concat("<"+Page.KARAF_NAMESPACE+":feature>"+ft.getName()+"</"+Page.KARAF_NAMESPACE+":feature>");
					r = r.concat("<"+Page.KARAF_NAMESPACE+":bundle start-level='"+ft.getStartLevel()+"' start='"+ft.isStart()+"'>"+ft.getBundle()+"</"+Page.KARAF_NAMESPACE+":bundle>");
					r = r.concat("</"+Page.KARAF_NAMESPACE+":feature>");
				}				
				r = r.concat("</"+Page.KARAF_NAMESPACE+":features>");

				r = r.concat("</karaf>");
			}
			if(container == Container.ANDROID){
				r = r.concat("<android>");
				r = r.concat(androidPart.getXML());
				r = r.concat("</android>");
			}
		}

		return r;
	}

	public enum Container{

		TOMCAT, EQUINOX, FELIX, OSGI_ANDROID, KARAF, ANDROID
	}
}