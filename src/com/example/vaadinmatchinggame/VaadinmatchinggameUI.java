package com.example.vaadinmatchinggame;

import java.io.File;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("vaadinmatchinggame")
public class VaadinmatchinggameUI extends UI{

	private final static Logger logger =
	          Logger.getLogger(VaadinmatchinggameUI.class.getName());
	 
	 private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	 private int numberOfFaces = 5;
	 private int size = 400;
	 private String PATH = basepath + "/WEB-INF/images/smile.png";
	 private boolean play = true;
	 private int counter = 0;
	 
	 private AbsoluteLayout leftLayout = new AbsoluteLayout();
	 private AbsoluteLayout rightLayout = new AbsoluteLayout();
	 private Label labelCounter = new Label("0");
	
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinmatchinggameUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		Page.getCurrent().setTitle("Matching Game");
		
		Label labelH1 = new Label("<span style=\"color:SteelBlue;\">M</span>atching "
				+ "<span style=\"color:Purple;\">G</span>ame!", ContentMode.HTML);
		Label labelH4 = new Label("Click on the extra smiling face on the <span>left</span>.",
				ContentMode.HTML);
		CssLayout layout = new CssLayout();
		
		labelH1.setStyleName("h2");
		labelH4.setStyleName("h4");
		labelCounter.setId("counter");
		leftLayout.setId("leftSide");
		rightLayout.setId("rightSide");
		
		layout.addComponent(labelH1);
		layout.addComponent(labelH4);
		layout.addComponent(labelCounter);
		layout.addComponent(leftLayout);
		layout.addComponent(rightLayout);
		
		setContent(layout);
		
		//addClickListener(this);
		
		
		UI.getCurrent().addClickListener(new ClickListener() {
			
			@Override
			public void click(ClickEvent event) {
				Notification.show("Game Over");
			}
		});
		
		generateFaces();
	}
	
	private void generateFaces () {
		FileResource resource = new FileResource(new File(PATH));
		Image face = new Image();
		
		leftLayout.removeAllComponents();
		rightLayout.removeAllComponents();
		
		for (int i = 0; i < numberOfFaces; i++){
			double randomTop = Math.random() * size;
			double randomLeft = Math.random() * size;

			face = new Image(null, resource);
			Image copyFace = new Image(null, resource);
			
			face.setAlternateText("smile face");
			face.setStyleName("img");
			
			copyFace.setAlternateText("smile face");
			copyFace.setStyleName("img");

			leftLayout.addComponent(face, "top:" + randomTop 
					+ "px; left:" + randomLeft + "px;");
			
			if (i != numberOfFaces - 1)
				rightLayout.addComponent(copyFace, "top:" + randomTop 
						+ "px; left:" + randomLeft + "px;");

		}

		face.addClickListener(new ClickListener() {
			
			@Override
			public void click(ClickEvent event) {
				UI.getCurrent().getListeners(ClickListener.class);
				numberOfFaces += 5;
				counter++;
				labelCounter.setValue("" + counter);
				generateFaces();
				Notification.show("Bien", Notification.Type.HUMANIZED_MESSAGE);
			}
		});

	}
	
	/*public class nextLevel implements ClickListener{
		@Override
		public void click(ClickEvent event) {
			
			numberOfFaces += 5;
			counter++;
			labelCounter.setValue("" + counter);
			generateFaces();
			Notification.show("Bien", Notification.Type.HUMANIZED_MESSAGE);
		}
		
		
	}*/
	
	public class GameOverEvent implements ClickListener{

		@Override
		public void click(ClickEvent event) {
			Notification.show("AAAAAAAAAAAAAAAAAAAA");
			
		}
		
	}
	

/*	@Override
	public void click(ClickEvent event) {
		counter = 0;
		labelCounter.setValue("" + counter);
		ConfirmDialog.show(getCurrent(), "Test", null);
		Notification.show("AAAAAAAAAAAAAAAAAAAA");
		
		
	}*/

}