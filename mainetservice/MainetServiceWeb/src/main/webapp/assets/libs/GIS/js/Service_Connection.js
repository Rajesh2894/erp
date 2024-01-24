var map, view;
var masterappconfigration = {
    //##################for development environment##################
    baseapiurlpath: 'http://localhost:11017/',
    baserootapiurlpath: 'http://localhost:11017',
    //baseapplicationurlpath: 'http://localhost:11017',
    baseapplicationurlpath: 'http://localhost:11017/creativeapps/gisniuasolution/defaultindex.html',
    dynamicmapserviceurl: "https://localhost/server/rest/services/GISDB/AdminBoundryWithLabel/MapServer",
    dynamicmapserviceOverlayUrl: "https://localhost/server/rest/services/GISDB/GISDB/MapServer",
    featurelayerurl: "https://services9.arcgis.com/pux4Mdcz6sFCrqYX/ArcGIS/rest/services/ValveIsolationTrace/FeatureServer/2",
    //featurelayerurl: "https://services.arcgis.com/V6ZHFr6zdgNZuVG0/ArcGIS/rest/services/NYCDemographics1/FeatureServer/0",
    basemap: 'https://services.arcgisonline.com/arcgis/rest/services/canvas/world_dark_gray_base/mapserver'
    //##################for development environment##################
};


function onloadgetdefaultmap(GISID) {
    try {
        require(["esri/Map","esri/views/MapView","esri/layers/FeatureLayer"],
           function (Map, MapView,FeatureLayer) {
                map = new Map({
                   basemap: "gray"
               });
                view = new MapView({
                   container: "sceneDiv",
                   map: map,
                   center: [85.81153477813736, 20.32490827130549],
                   zoom: 16,
                   padding: {
                       right: 300
                   }
               });
               const listNode = document.getElementById("ABMI_Graphics");
			   
               /********************
                * Add feature layer
                ********************/
               // Create the PopupTemplate
               const popupTemplate = { // autocasts as new PopupTemplate()
                   title: "Service Connection, Facility Identifier: {FACILITYID}",
                   content: [{
                       type: "fields",
                       fieldInfos: [{
                           fieldName: "FACILITYID",
                           label: "Facility Identifier",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       }, {
                           fieldName: "ACCOUNTID",
                           label: "Account Number",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       }, {
                           fieldName: "METSERVICE",
                           label: "Metered Service",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       }, {
                           fieldName: "SERVICETYPE",
                           label: "Service Type",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "INSTALLDATE",
                           label: "Install Date",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "LOCDESC",
                           label: "Location Description",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "ROTATION",
                           label: "Rotation",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "LOCATIONID",
                           label: "Location Identifier",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "CRITICAL",
                           label: "Critical Customer",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "ENABLED",
                           label: "Enabled",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "ACTIVEFLAG",
                           label: "Active Flag",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "OWNEDBY",
                           label: "Owned By",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "MAINTBY",
                           label: "Managed By",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "LASTUPDATE",
                           label: "Last Update Date",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "LASTEDITOR",
                           label: "Last Editor",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "Billing",
                           label: "Billing",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "WaterQuality",
                           label: "Water Quality",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "Coverage",
                           label: "Coverage",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "WaterType",
                           label: "Water Type",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "CustomerName",
                           label: "Customer Name",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "ContactNumber",
                           label: "Contact Number",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "Jun",
                           label: "Jun",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "Jul",
                           label: "Jul",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "Aug",
                           label: "Aug",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "WaterConsumptions",
                           label: "LPCD",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "Ward",
                           label: "Ward",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "MonthlyLPCD",
                           label: "Monthly LPCD",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "BilledAmount",
                           label: "Billed Amount",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "Amount_Paid",
                           label: "Amount_Paid",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       },{
                           fieldName: "LPCD_Consumed",
                           label: "LPCD_Consumed",
                           format: {
                               places: 0,
                               digitSeparator: true
                           }
                       }]
                   }]
               };

               // Create the FeatureLayer using the popupTemplate
               const featureLayer = new FeatureLayer({
                   url: masterappconfigration.featurelayerurl,
                   outFields: ["*"],
                   popupTemplate: popupTemplate
               });
               map.add(featureLayer);
               let graphics;
               view.whenLayerView(featureLayer).then(function (layerView) {
                   layerView.watch("updating", function (value) {
                       if (!value) { // wait for the layer view to finish updating
                           // query all the features available for drawing.
                           layerView.queryFeatures({
                               geometry: view.extent,
                               returnGeometry: true
                           }).then(function (results) {
                               graphics = results.features;
                               const fragment = document.createDocumentFragment();
                               graphics.forEach(function (result, index) {
                                   const attributes = result.attributes;
								   
                                   const name = attributes.FACILITYID; /* + " (" + attributes.sensor_id + ")" */
                                   // Create a list zip codes in NY
								   if(name==GISID)
								   { 
							   const li = document.createElement("li");
                                   li.classList.add("panel-result");
                                   li.tabIndex = 0;
                                   li.setAttribute("data-result-id", index);
                                   li.textContent = name;
                                   fragment.appendChild(li); 
								   
								   }
                               });
                               // Empty the current list
                               listNode.innerHTML = "";
                               listNode.appendChild(fragment);
                           }).catch(function (error) {
                               console.error("query failed: ", error);
                           });
                       }
                   });
               });
               // listen to click event on the zip code list
               listNode.addEventListener("click", onListClickHandler);
               function onListClickHandler(event) {
                   const target = event.target;
                   const resultId = target.getAttribute("data-result-id");
				   //const resultId = target.getAttribute("WSRV-1719");
                   const result = resultId && graphics && graphics[parseInt(resultId, 10)];
                   if (result) {
                       view.goTo({
                           target: [result.geometry.longitude, result.geometry.latitude],
                           zoom: view.zoom
                       }).then(function () {
                           view.popup.open({
                               features: [result],
                               location: result.geometry.centroid
                           });
                       });
                       //view.goTo(result.geometry.extent.expand(2))
                       //  .then(function () {
                       //      view.popup.open({
                       //          features: [result],
                       //          location: result.geometry.centroid
                       //      });
                       //  });
                   }
               }
           });
    } catch (e) {
        var error = e;
        error = error;
    }
}
