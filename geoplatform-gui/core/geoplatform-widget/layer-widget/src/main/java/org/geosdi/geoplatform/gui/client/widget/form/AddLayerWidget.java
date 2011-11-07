/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2011 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details. You should have received a copy of the GNU General
 * Public License along with this program. If not, see http://www.gnu.org/licenses/
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library. Thus, the terms and
 * conditions of the GNU General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this library give you permission
 * to link this library with independent modules to produce an executable, regardless
 * of the license terms of these independent modules, and to copy and distribute
 * the resulting executable under terms of your choice, provided that you also meet,
 * for each linked independent module, the terms and conditions of the license of
 * that module. An independent module is a module which is not derived from or
 * based on this library. If you modify this library, you may extend this exception
 * to your version of the library, but you are not obligated to do so. If you do not
 * wish to do so, delete this exception statement from your version.
 *
 */
package org.geosdi.geoplatform.gui.client.widget.form;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import org.geosdi.geoplatform.gui.client.widget.GeoPlatformWindow;
import org.geosdi.geoplatform.gui.client.widget.toolbar.mediator.MediatorToolbarTreeAction;
import org.geosdi.geoplatform.gui.configuration.message.GeoPlatformMessage;
import org.geosdi.geoplatform.gui.model.tree.GPBeanTreeModel;
import org.geosdi.geoplatform.gui.plugin.tree.addlayer.AddLayerPluginManager;
import org.geosdi.geoplatform.gui.plugin.tree.addlayer.IAddLayerPlugin;

/**
 * @author Nazzareno Sileno - CNR IMAA geoSDI Group
 * @email nazzareno.sileno@geosdi.org
 */
public class AddLayerWidget extends GeoPlatformWindow {

    private ListStore<IAddLayerPlugin> store;
    private TreePanel<GPBeanTreeModel> tree;
    private ListView<IAddLayerPlugin> listView;
    private ContentPanel centralPanel;

    /**
     *@param theTree 
     * 
     */
    public AddLayerWidget(TreePanel<GPBeanTreeModel> theTree) {
        super(true);
        this.tree = theTree;
    }

    private ListView<IAddLayerPlugin> generateListView() {
        listView = new ListView<IAddLayerPlugin>() {

            @Override
            protected IAddLayerPlugin prepareData(IAddLayerPlugin plugin) {
//                Photo photo = plugin.getBean();
//                long size = photo.getSize() / 1000;
                plugin.set("shortName", Format.ellipse(plugin.getTooltip(), 30));
//                plugin.set("sizeString", NumberFormat.getFormat("#0").format(size) + "k");
//                plugin.set("dateString", DateTimeFormat.getMediumDateTimeFormat().format(photo.getDate()));
//                plugin.set("path", GWT.getHostPageBaseURL() + photo.getPath());
                return plugin;
            }
        };
        listView.addStyleName("overview-page");
        listView.setItemSelector(".project-box");
        listView.setOverStyle("sample-over");
        listView.setSelectStyle("none");
        listView.setBorders(false);
        listView.setStore(store);

        listView.getSelectionModel().addSelectionChangedListener(
                new SelectionChangedListener<IAddLayerPlugin>() {

                    @Override
                    public void selectionChanged(SelectionChangedEvent<IAddLayerPlugin> se) {
                        if (se.getSelectedItem() != null) {
                            if (se.getSelectedItem().getAction(tree).isEnabled()) {
                                ButtonEvent be = new ButtonEvent(null);
                                be.setType(Events.Select);
                                se.getSelectedItem().getAction(tree).handleEvent(be);
                                hide();
                            } else {
                                GeoPlatformMessage.errorMessage("Function Disabled", se.getSelectedItem().getMessageToEnable());
                            }
                        }
                        listView.getSelectionModel().deselectAll();
                    }
                });

        setListViewProperties();

        return listView;
    }

    private void setListViewProperties() {
        StringBuilder sb = new StringBuilder();
        sb.append("<tpl for=\".\">");
        sb.append("<div class='project-box' style='padding-top: 4px;border: none'>");
        sb.append("<div class='thumbd' title='{tooltip}'>{image}</div>");
        sb.append("<div>{name}</div>");
        sb.append("</div></tpl>");

        listView.setTemplate(sb.toString());

        listView.setSize(615, 228);
    }

    @Override
    public void setWindowProperties() {
        super.setHeading("Add Layers Window");
        super.setScrollMode(Scroll.AUTOY);
        super.setResizable(false);
        this.store = new ListStore<IAddLayerPlugin>();
    }

    @Override
    public void initSize() {
        setSize(615, 232);
    }

    @Override
    public void addComponent() {
        for (IAddLayerPlugin plugin : AddLayerPluginManager.getWindowPlugins()) {
            plugin.initPlugin(tree);
            this.store.add(plugin);
        }
    }

    @Override
    public void finalizeInitOperations() {
        super.finalizeInitOperations();
        this.centralPanel = new ContentPanel(new FlowLayout(0));
        this.centralPanel.setHeaderVisible(false);
        this.centralPanel.setFrame(true);
        this.centralPanel.setSize(602, 198);
        this.centralPanel.add(this.generateListView());
        super.add(this.centralPanel);
    }

    @Override
    public void show() {
        super.show();
        MediatorToolbarTreeAction.getInstance().setAddLayerVisible(true);
        MediatorToolbarTreeAction.getInstance().manageAddLayerPluginAction(tree.getSelectionModel().getSelectedItem());
    }

    @Override
    public void hide(Button buttonPressed) {
        super.hide(buttonPressed);
        MediatorToolbarTreeAction.getInstance().setAddLayerVisible(false);
    }

    /**
     * @return the tree
     */
    public TreePanel<GPBeanTreeModel> getTree() {
        return tree;
    }
}