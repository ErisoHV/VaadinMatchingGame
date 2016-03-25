package com.example.vaadinmatchinggame;

import java.io.File;

import javax.servlet.annotation.WebServlet;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("vaadinmatchinggame")
public class VaadinmatchinggameUI extends UI{

	 private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	 private int numberOfFaces = 5;
	 private int size = 400;
	 private String PATH = basepath + "/WEB-INF/images/smile.png";
	 private int counter = 0;
	 
	 private AbsoluteLayout leftLayout = new AbsoluteLayout();
	 private AbsoluteLayout rightLayout = new AbsoluteLayout();
	 private Label labelCounter = new Label("0");
	 
	 private Image correctFace = new Image();
	
	
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
		final CssLayout layout = new CssLayout();
		
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
		
		UI.getCurrent().setId("body");
		
		layout.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (event.getButton()== event.BUTTON_LEFT){
					Component child = event.getChildComponent();
					if (child != null){
						ConfirmDialog.show(UI.getCurrent(),"Game Over","Do you want to play again?", "Ok",
								"Cancel", new ConfirmDialog.Listener() {
							@Override
							public void onClose(ConfirmDialog dialog) {
								if (dialog.isConfirmed()){
									counter = 0;
									labelCounter.setValue("" + counter);
									numberOfFaces = 5;
									generateFaces();
								}
								else{
									correctFace.setEnabled(false);
									correctFace.setStyleName("correctImgGameOver");
									layout.setEnabled(false);
								}
							}
						});
	
						
					}					
				}
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
			face.setId("img" + 1);
			
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
		
		correctFace = face;
		
		correctFace.addClickListener(new ClickListener() {
			@Override
			public void click(ClickEvent event) {
				if (event.getButton()== event.BUTTON_LEFT){
					numberOfFaces += 5;
					counter++;
					labelCounter.setValue("" + counter);
					generateFaces();
				}
				
			}
		});

	}
}
