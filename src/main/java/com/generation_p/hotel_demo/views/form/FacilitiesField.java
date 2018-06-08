package com.generation_p.hotel_demo.views.form;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings ("serial")
public class FacilitiesField extends CustomField<String> {

    private Set<String> facilities = new TreeSet<>();
    private String caption = "Facilities";
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
        layout.setMargin(false);
        
        HorizontalLayout adding = new HorizontalLayout();
        Button add = new Button("Add");
        TextField input = new TextField();
        
        input.setPlaceholder("Input facility");
        input.addValueChangeListener(l -> {
            add.setEnabled(!input.isEmpty());
        });
        input.setWidth("100%");
        
        add.setEnabled(false);
        add.addClickListener(l -> {
            facilities.add(input.getValue());
            add.setEnabled(false);
            input.clear();
            doSetValue(getValue());
        });
        add.setWidth("100%");
        
        adding.addComponents(input, add);
        adding.setWidth("100%");
        layout.addComponent(adding);
        layout.setWidth("100%");
        return layout;
    }

    @Override
    protected void doSetValue (String value) {
        layout.removeAllComponents();
        initContent();
        facilities.clear();
        
        if (value != null) Arrays.asList(value.split(",")).forEach(val -> facilities.add(val));
        else return;
        
        Iterator<String> iterator = facilities.iterator();
        while (iterator.hasNext()) {
            currentFaclilityBuilder(iterator.next());
        }
    }

    private void currentFaclilityBuilder (String facility) {
        HorizontalLayout current = new HorizontalLayout();
        
        NativeButton currentCloseButton = new NativeButton();
        currentCloseButton.setStyleName(ValoTheme.BUTTON_SMALL);
        currentCloseButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        currentCloseButton.setWidth("100%");
        currentCloseButton.setIcon(VaadinIcons.CLOSE_CIRCLE_O);
        currentCloseButton.addClickListener(l -> {
           facilities.remove(facility);
           layout.removeComponent(current);
        });
        
        NativeButton currentLabel = new NativeButton(facility);
        currentLabel.setStyleName(ValoTheme.BUTTON_SMALL);
        currentLabel.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        currentLabel.setWidth("100%");
        currentLabel.setEnabled(false);
        
        current.setWidth("100%");
        current.setMargin(false);
        current.addComponents(currentLabel, currentCloseButton);
        
        layout.addComponent(current);
    }
}
