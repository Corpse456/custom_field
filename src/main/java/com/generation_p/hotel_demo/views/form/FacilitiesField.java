package com.generation_p.hotel_demo.views.form;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.selenium.WebElement;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings ("serial")
public class FacilitiesField extends CustomField<String> {

    private Set<String> facilities = new TreeSet<>();
    private String caption = "Facilities";
    private NativeButton del = new NativeButton();
    private Label label = new Label("Fuck");
    private VerticalLayout layout = new VerticalLayout();
    
    @Override
    public String getValue () {
        StringBuilder facilitiesIntoBase = new StringBuilder();
        Iterator<String> iterator = facilities.iterator();
        while (iterator.hasNext()) {
            facilitiesIntoBase.append(iterator.next());
            if (iterator.hasNext()) facilitiesIntoBase.append(",");
        }
        return facilitiesIntoBase.toString();
    }

    @Override
    protected Component initContent () {
        super.setCaption(caption);
        
        /*del.setStyleName(ValoTheme.BUTTON_SMALL);
        del.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        del.setWidth("100%");
        del.setIcon(VaadinIcons.CLOSE_CIRCLE_O);
        
        label.setWidth("100%");
        layout.setWidth("100%");
        
        layout.setMargin(false);
        
        layout.addComponents(label, del);*/
        return layout;
    }

    @Override
    protected void doSetValue (String value) {
        Arrays.asList(value.split(",")).forEach(val -> facilities.add(val));;
        //label.setValue(facilities);
    }
}
