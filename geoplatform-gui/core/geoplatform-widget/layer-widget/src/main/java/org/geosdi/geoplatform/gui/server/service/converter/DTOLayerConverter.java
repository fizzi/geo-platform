/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2013 geoSDI Group (CNR IMAA - Potenza - ITALY).
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
package org.geosdi.geoplatform.gui.server.service.converter;

import com.google.common.collect.Lists;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.geosdi.geoplatform.core.model.GPBBox;
import org.geosdi.geoplatform.core.model.GPFolder;
import org.geosdi.geoplatform.core.model.GPProject;
import org.geosdi.geoplatform.gui.client.model.memento.save.bean.MementoFolder;
import org.geosdi.geoplatform.gui.client.model.projects.GPClientProject;
import org.geosdi.geoplatform.gui.client.widget.time.LayerTimeFilterWidget;
import org.geosdi.geoplatform.gui.configuration.map.client.geometry.BBoxClientInfo;
import org.geosdi.geoplatform.gui.configuration.map.client.layer.ClientRasterInfo;
import org.geosdi.geoplatform.gui.configuration.map.client.layer.ClientVectorInfo;
import org.geosdi.geoplatform.gui.configuration.map.client.layer.GPFolderClientInfo;
import org.geosdi.geoplatform.gui.configuration.map.client.layer.GPLayerClientInfo;
import org.geosdi.geoplatform.gui.configuration.map.client.layer.IGPFolderElements;
import org.geosdi.geoplatform.gui.model.tree.GPStyleStringBeanModel;
import org.geosdi.geoplatform.gui.model.user.GPSimpleUser;
import org.geosdi.geoplatform.gui.shared.GPLayerType;
import org.geosdi.geoplatform.responce.AccountProjectPropertiesDTO;
import org.geosdi.geoplatform.responce.FolderDTO;
import org.geosdi.geoplatform.responce.IElementDTO;
import org.geosdi.geoplatform.responce.ProjectDTO;
import org.geosdi.geoplatform.responce.RasterLayerDTO;
import org.geosdi.geoplatform.responce.ShortAccountDTO;
import org.geosdi.geoplatform.responce.ShortLayerDTO;
import org.geosdi.geoplatform.responce.UserDTO;
import org.geosdi.geoplatform.responce.VectorLayerDTO;
import org.geosdi.geoplatform.responce.collection.TreeFolderElements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Simple Class to convert Web-Services beans model in DTO beans Client.
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Component(value = "dtoLayerConverter")
public class DTOLayerConverter {

    @Autowired
    private GeoServerRESTReader sharedRestReader;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ArrayList<GPFolderClientInfo> convertOnlyFolders(Collection<FolderDTO> folders) {
        ArrayList<GPFolderClientInfo> foldersClient = Lists.newArrayList();
        if (folders != null) {
            for (Iterator<FolderDTO> it = folders.iterator(); it.hasNext();) {
                GPFolderClientInfo folder = this.convertFolderElement(it.next());
                foldersClient.add(folder);
            }
        }
        return foldersClient;
    }

    private List<IGPFolderElements> convertFolderElements(List<IElementDTO> folderElements) {
        List<IGPFolderElements> clientFolderElements = Lists.newArrayList();
        Iterator<IElementDTO> iterator = folderElements.iterator();
        while (iterator.hasNext()) {
            clientFolderElements.add(this.convertElement(iterator.next()));
        }
        return clientFolderElements;
    }

    public ArrayList<IGPFolderElements> convertFolderElements(TreeFolderElements folderElements) {
        ArrayList<IGPFolderElements> clientFolderElements = Lists.newArrayList();
        Iterator<IElementDTO> iterator = folderElements.iterator();
        while (iterator.hasNext()) {
            clientFolderElements.add(this.convertElement(iterator.next()));
        }
        return clientFolderElements;
    }

    private IGPFolderElements convertElement(IElementDTO element) {
        IGPFolderElements folderElement = null;
        if (element instanceof RasterLayerDTO) {
            folderElement = this.convertRasterElement((RasterLayerDTO) element);
        } else if (element instanceof VectorLayerDTO) {
            folderElement = this.convertVectorElement(
                    (VectorLayerDTO) element);
        } else if (element instanceof FolderDTO) {
            folderElement = this.convertFolderElement((FolderDTO) element);
        }
        return folderElement;
    }

    private ClientRasterInfo convertRasterElement(RasterLayerDTO rasterDTO) {
        ClientRasterInfo raster = new ClientRasterInfo();
        this.convertToLayerElementFromLayerDTO(raster, rasterDTO);
        raster.setLayerType(GPLayerType.WMS);
        raster.setOpacity(rasterDTO.getOpacity());
        ArrayList<GPStyleStringBeanModel> styles = Lists.newArrayList();
        GPStyleStringBeanModel style;
        for (String styleString : rasterDTO.getStyleList()) {
            style = new GPStyleStringBeanModel();
            style.setStyleString(styleString);
            styles.add(style);
        }
        raster.setStyles(styles);
        return raster;
    }

    private ClientVectorInfo convertVectorElement(VectorLayerDTO vectorDTO) {
        ClientVectorInfo vector = new ClientVectorInfo();
        this.convertToLayerElementFromLayerDTO(vector, vectorDTO);
        this.setVectorLayerType(vector, vectorDTO.getLayerType());
        vector.setFeatureType(vectorDTO.getName());
        return vector;
    }

    private void convertToLayerElementFromLayerDTO(GPLayerClientInfo layer, ShortLayerDTO layerDTO) {
        layer.setAbstractText(layerDTO.getAbstractText());
        layer.setBbox(this.convertBbox(layerDTO.getBbox()));
        layer.setChecked(layerDTO.isChecked());
        layer.setCrs(layerDTO.getSrs());
        layer.setDataSource(layerDTO.getUrlServer());
        layer.setId(layerDTO.getId());
        layer.setLayerName(layerDTO.getName());
        layer.setTitle(layerDTO.getTitle());
        layer.setAlias(layerDTO.getAlias());
        layer.setCqlFilter(layerDTO.getCqlFilter());
        if (layerDTO.getTimeFilter() != null) {
            layer.setTimeFilter(layerDTO.getTimeFilter());
            try {
                String dimension = this.sharedRestReader.getDimensions(layerDTO.getTitle());
                if (!dimension.contains("<h2>")) {
                    List<String> dimensionList = Lists.newArrayList(dimension.split(","));

                    String[] timeFilterSplitted = layerDTO.getTimeFilter().split("/");
                    int startDimensionPosition = Integer.parseInt(timeFilterSplitted[0]);

                    String variableTimeFilter = dimensionList.get(dimensionList.size() - startDimensionPosition - 1);
                    if (timeFilterSplitted.length > 1) {
                        int endDimensionPosition = Integer.parseInt(timeFilterSplitted[1]);
                        variableTimeFilter += "/" + dimensionList.get(dimensionList.size() - endDimensionPosition - 1);
                    }
                    layer.setVariableTimeFilter(variableTimeFilter);
                    String layerAlias;
                    if (layerDTO.getAlias() != null
                            && layerDTO.getAlias().indexOf(LayerTimeFilterWidget.LAYER_TIME_DELIMITER) != -1) {
                        layerAlias = layerDTO.getAlias().substring(0,
                                layerDTO.getAlias().indexOf(LayerTimeFilterWidget.LAYER_TIME_DELIMITER));
                    } else {
                        layerAlias = layerDTO.getTitle();
                    }
                    layer.setAlias(layerAlias + LayerTimeFilterWidget.LAYER_TIME_DELIMITER
                            + layer.getVariableTimeFilter() + "]");
                }
            } catch (NumberFormatException nfe) {
            } catch (MalformedURLException nfe) {
                logger.error("Impossible to retrieve time filter executing call with "
                        + "geoServerManager: " + nfe);
            }
        }
        // layer.setzIndex(layerDTO.getPosition());
    }

    private GPFolderClientInfo convertFolderElement(FolderDTO folderDTO) {
        GPFolderClientInfo folder = new GPFolderClientInfo();
        folder.setLabel(folderDTO.getName());
        folder.setId(folderDTO.getId());
        // folder.setzIndex(folderDTO.getPosition());
        folder.setNumberOfDescendants(folderDTO.getNumberOfDescendants());
        folder.setChecked(folderDTO.isChecked());
        folder.setExpanded(folderDTO.isExpanded());
        folder.setFolderElements(this.convertFolderElements(folderDTO.getElementList()));
        return folder;
    }

    private BBoxClientInfo convertBbox(GPBBox gpBbox) {
        return new BBoxClientInfo(gpBbox.getMinX(), gpBbox.getMinY(),
                gpBbox.getMaxX(), gpBbox.getMaxY());
    }

    private void setVectorLayerType(ClientVectorInfo vector, GPLayerType layerType) {
        switch (layerType) {
            case POINT:
                vector.setLayerType(GPLayerType.POINT);
                break;
            case LINESTRING:
                vector.setLayerType(GPLayerType.LINESTRING);
                break;
            case POLYGON:
                vector.setLayerType(GPLayerType.POLYGON);
                break;
            case MULTIPOINT:
                vector.setLayerType(GPLayerType.MULTIPOINT);
                break;
            case MULTILINESTRING:
                vector.setLayerType(GPLayerType.MULTILINESTRING);
                break;
            case MULTIPOLYGON:
                vector.setLayerType(GPLayerType.MULTIPOLYGON);
                break;
            default:
                System.out.println("### No Layer Type ###");
        }
    }

    public GPFolder convertMementoFolder(MementoFolder memento) {
        GPFolder gpFolder = new GPFolder();
        gpFolder.setId(memento.getIdBaseElement());
        gpFolder.setName(memento.getFolderName());
        gpFolder.setNumberOfDescendants(memento.getNumberOfDescendants());
//        if (memento.getIdParent() != null) {
//            GPFolder parent = new GPFolder();
//            parent.setId(memento.getIdParent());
//            gpFolder.setParent(parent);
//        }
        gpFolder.setPosition(memento.getzIndex());
        /*
         * TODO: Once implemented shared function you must set this property
         * gpFolder.setShared(true);
         */
        return gpFolder;
    }

    public GPProject convertToGProject(GPClientProject clientProject) {
        GPProject project = new GPProject();
        project.setName(clientProject.getName());
        project.setShared(clientProject.isShared());
        return project;
    }

    public AccountProjectPropertiesDTO convertToAccountProjectPropertiesDTO(Long accountID,
            GPClientProject project) {
        AccountProjectPropertiesDTO dto = new AccountProjectPropertiesDTO();
        dto.setAccountID(accountID);
        dto.setProjectID(project.getId());
        dto.setProjectName(project.getName());
        dto.setProjectVersion(project.getVersion());
        dto.setDefaultProject(project.isDefaultProject());
        dto.setShared(project.isShared());
        return dto;
    }

    public GPClientProject convertToGPClientProject(ProjectDTO projectDTO) {
        GPClientProject clientProject = new GPClientProject();
        clientProject.setId(projectDTO.getId());
        clientProject.setName(projectDTO.getName());
        clientProject.setNumberOfElements(projectDTO.getNumberOfElements());
        if (projectDTO.isDefault() != null) {
            clientProject.setDefaultProject(projectDTO.isDefault());
        }
        if (projectDTO.isShared() != null) {
            clientProject.setShared(projectDTO.isShared());
        }
        ShortAccountDTO owner = projectDTO.getOwner();
        if (owner != null && owner instanceof UserDTO) {
            clientProject.setOwner(this.convertToGPSimpleUser((UserDTO) owner));
        }
        clientProject.setRootFolders(this.convertOnlyFolders(projectDTO.getRootFolders()));
        return clientProject;
    }

    public GPClientProject convertToGPCLientProject(ProjectDTO projectDTO,
            String imageURL) {
        GPClientProject clientProject = this.convertToGPClientProject(projectDTO);
        clientProject.setImage(imageURL);
        return clientProject;
    }

    public List<GPSimpleUser> convertToGPSimpleUser(List<ShortAccountDTO> shortAccountList) {
        List<GPSimpleUser> listSimpleUser = Lists.newArrayList();
        for (ShortAccountDTO shortAccont : shortAccountList) {
            if (shortAccont instanceof UserDTO) {
                listSimpleUser.add(this.convertToGPSimpleUser((UserDTO) shortAccont));
            }
        }
        return listSimpleUser;
    }

    private GPSimpleUser convertToGPSimpleUser(UserDTO userDTO) {
        GPSimpleUser user = new GPSimpleUser();
        user.setId(userDTO.getId());
        user.setOrganization(userDTO.getOrganization());
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmailAddress());
        return user;
    }
}
