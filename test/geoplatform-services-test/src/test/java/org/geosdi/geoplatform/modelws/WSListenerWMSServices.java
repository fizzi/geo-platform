/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2012 geoSDI Group (CNR IMAA - Potenza - ITALY).
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
package org.geosdi.geoplatform.modelws;

import java.util.concurrent.TimeUnit;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.geosdi.geoplatform.configurator.cxf.server.ServerInterceptorStrategyFactory;
import org.geosdi.geoplatform.connectors.ws.wms.GPWMSClientTestConnector;
import org.geosdi.geoplatform.services.GPWMSService;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email  giuseppe.lascaleia@geosdi.org
 * 
 * @author Michele Santomauro - CNR IMAA geoSDI Group
 * @email michele.santomauro@geosdi.org
 */
public class WSListenerWMSServices implements TestExecutionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //
    private GPWMSService gpWMSClient;
    private Endpoint endpoint;
    private Bus bus;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        logger.info("\n\t@@@ WSListenerServices.beforeTestClass @@@");
        
        ApplicationContext appContext = testContext.getApplicationContext();

        GPWMSClientTestConnector geoPlatformWMSWSClient = (GPWMSClientTestConnector) appContext.getBean("gpWMSClient");
        Assert.assertNotNull("geoPlatformWMSWSClient is NULL", geoPlatformWMSWSClient);
        gpWMSClient = geoPlatformWMSWSClient.getEndpointService();

        GPWMSService geoPlatformWMSService = (GPWMSService) appContext.getBean("wmsService");
        Assert.assertNotNull("geoPlatformWMSService is NULL", geoPlatformWMSService);

        Object implementor = geoPlatformWMSService;
        SpringBusFactory bf = new SpringBusFactory();
        bus = bf.createBus();

        bus.getInInterceptors().add(new LoggingInInterceptor());
        bus.getOutInterceptors().add(new LoggingOutInterceptor());

        ServerInterceptorStrategyFactory serverInterceptorStrategyFactory = (ServerInterceptorStrategyFactory) testContext.getApplicationContext().getBean("serverInterceptorStrategyFactory");
        Assert.assertNotNull("serverInterceptorStrategyFactory is NULL", serverInterceptorStrategyFactory);

        bus.getInInterceptors().add(serverInterceptorStrategyFactory.getSecurityInInterceptor());
        bus.getOutInterceptors().add(serverInterceptorStrategyFactory.getSecurityOutInterceptor());

        BusFactory.setDefaultBus(bus);
        String serverAddress = geoPlatformWMSWSClient.getAddress();
        endpoint = Endpoint.publish(serverAddress, implementor);

        logger.info("\n\t@@@ Server ready... @@@");
    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
        logger.info("\n\t@@@ WSWMSListenerWMSServices.prepareTestInstance @@@");

        ServiceWMSTest testInstance = (ServiceWMSTest) testContext.getTestInstance();
        testInstance.setGpWMSClient(gpWMSClient);
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        logger.info("\n\t@@@ WSListenerWMSServices.afterTestClass @@@");

        endpoint.stop();
        bus.shutdown(true);
        // Wait to be sure that the endpoint was shutdown properly
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
    }
}