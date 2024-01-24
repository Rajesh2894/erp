/* global top_four_insights:true, deepdive:true, data_api:true, deepdive:true, image_dict:true*/
var default_page_vids = [{"dist": "Anjaw", "active": "active", "vid": "anjaw", "text": "I live in ChangumÂ bastiÂ of Anjaw district. Earlier we did not have a toilet at home because of which we were compelled to defecate in the open along with pigs. We had to accompany our kids Â whenever they needed to defecate. It was worse during monsoon as it was troublesome for us to go out while it poured. It was painful to go out everytime for defecation especially during the time of illness. Now that a toilet has been constructed at my home, the situation has become better for us. We have made sure that a water pipe discharges the excreta outside the house and the cleanliness of the toilet is maintained. Today, children go alone for defecation. We are relieved from the constant stress now.-Â Balem Sapol"},
{"dist": "Coimbatore", "active": "", "vid": "coimbatore", "text": "I am from Vatavagar village. Post the launch of Swachh Bharat Mission, we have seen construction of many toilets in our village. Under the provision of SBM, we got an allowance of INR 12,000 to construct a toilet at our home. Earlier, we were given less than INR 2000 to construct basic toilet related infrastructure. With the allowance of INR 12,000 we were able to get a good quality toilet constructed at our home. The scheme has been particularly beneficial for poor people. With the aid of field staff and engineers in the district, villagers started building toilets at their homes and our village became ODF. Along with building toilets, awareness campaigns were also organized in our village. Post SBM, 5 villages in our block have been declared ODF. -Sampath Kumar"},
{"dist": "Indore", "active": "", "vid": "indore", "text": "I am from Baigram village. When we got to know from our elders that many villagers go out in the open for defecation, we grouped ourselves as â€˜Vaanar Senaâ€™ to stop this age old menace. As a daily activity, our team used to get up at 5:00 am in the morning and set out in different directions in the village. We accosted people who were found defecating in the open by blowing Â whistles and throwing away their water mugs. Because of this, people who defecated in open felt ashamed and ran away. Post the initiatives taken by our team, nobody goes out in the open for defecation. Earlier, people often used to get sick but now its prevalence has also reduced.-Â Yashraj"},
{"dist": "Mahesana", "active": "", "vid": "mahesana", "text": "Before the construction of toilets in our village, we couldnâ€™t go for defecation in the open during the day and prefer to defecate during night. During monsoon we were scared of poisonous animals.Â  If any family member fell sick, it was difficult to take them out for defecation. Later an awareness campaign was organised. We were guided to not go out to defecate in the open as open defecation leads to onset of diseases. So, instead of spending on medical expenses we decided to spend on toilet construction. The condition of my village has improved substantially because of construction of toilets. The village is cleaner and incidences of disease have reduced.-Meena Behen Joshi"},
{"dist": "East Midnapur", "active": "", "vid": "midnapur_east", "text": "I study in South Deshbandhu School. There was a toilet in my school but there was no toilet at home. So, I could use the one at school at least. I used to face a lot of problems because of this, hence I asked my father to get a toilet constructed at home. My parents and I now use the toilet at home. We do not face any difficulty now.-Avantikapa"}
]
var rankcolor = {'Vacant Blocks in District': '#3AB54B', 'Allocated Blocks in District': '#FEE63C'},
    gmap, svg, active = d3.select(null)

function mapify() {
  $('.tooltip').remove();
  d3.json('assets/libs/indiaMap/india-states.json?v=2', function(err, json) {
    // Get the first shape in the map as GeoJSON
    var toposhape = topojson.feature(json, d3.values(json.objects)[0])
    // Draw the map
    gmap = G.map()
        .width(450)
        .height(500)
        .shape(toposhape)
    svg = d3.select('#map-path');
    svg.selectAll('*').remove();

    gmap.map(svg)
        .attr('fill', '#ccc')
        .attr('stroke', '#fff')
        //.attr('href', function(d){return ('?state=' + encodeURIComponent(d.properties.ST_NM.toUpperCase()));})
        //.attr('class', 'urlfilter')
        .attr('stroke-width', '1px')
        .on("click", zoomin)

    d3.select("rect.background")
      .on("click", zoomout)

    var projection = gmap.projection()
    _.each(filtered_data, function(city) {
      var _title = '<table class="table table-bordered table-tooltip"><tbody><tr><td colspan="2" style="color: #fff;">' + city.DISTRICT + '</td></tr></tbody></table>'
      var xy = projection([+city.LONGITUDE, +city.LATITUDE])
      svg.append('circle')
          .attr('class', 'urlfilter')
		  .attr('onclick', 'return getblockdetails(this);')
          .attr('href', '?city=' + encodeURIComponent(city.DISTRICT) + '&state=' + encodeURIComponent(city.STATE) + '&range=' + encodeURIComponent(city.RANGE))
          .attr('cx', xy[0])
          .attr('cy', xy[1])
          .attr('r', 5)
          .attr('fill', city.COLOR)
          .attr('data-city', city.DISTRICT)
          .attr('title',  _title)
          .style('cursor', 'pointer')
    })
    maplegend()
    broomify(filtered_data)
  })
}

function maplegend() {
  var rectw = 40,
      recth = 20,
      space = 5,
      height = recth + space,
      offset =  70,
      horz = 350 -2 * rectw,
      vert = 450
  var selected_area = $('#viewMode').val();
  if(selected_area === undefined){selected_area = 'VACANT'}
  else{ selected_area = selected_area }

  d3.select('#map-path')
    .append('g')
    .attr('transform', 'translate(' + horz + ',' + (vert - offset - height) + ')')
    .append('text')
      .attr('x', rectw - space)
      .attr('y', recth)
      .style('font-size', 12)
      .attr('fill', '#000')
      .text('Rank')

  var legend_data = '';
  if(selected_area === 'VACANT'){
    legend_data = ['Vacant Blocks in District']
  }
  else{
    legend_data = ['Allocated Blocks in District']
  }
  var legend = d3.select('#map-path')
    .selectAll('.legend')
    .data(legend_data)
    .enter()
    .append('g')
    .attr('class', 'legend')
    .attr('fill', '#000')
    .attr('transform', function(d, i) {
      return 'translate(' + horz + ',' + (vert + i * height - offset) + ')'
    })

  legend.append('rect')
    .attr('width', rectw)
    .attr('height', recth)
    .style('fill', function(d){ return rankcolor[d] })

  legend.append('text')
    .attr('x', rectw + space)
    .attr('y', recth - space)
    .style('font-size', 12)
    .text(function(d) {return d;})
}

function zoomin(d) {
  if(active.node() === this){return zoomout()}
  active.classed("active", false)
  active = d3.select(this).classed("active", true)
  var bounds = gmap.path().bounds(d),
      dx = bounds[1][0] - bounds[0][0],
      dy = bounds[1][1] - bounds[0][1],
      x = (bounds[0][0] + bounds[1][0]) / 2,
      y = (bounds[0][1] + bounds[1][1]) / 2,
      width = 520, height = 600,
      scale = Math.min(.5 / Math.max(dx / width, dy / height), 10),
      translate = [width / 2 - scale * x, height / 2 - scale * y]

   svg.transition()
      .duration(750)
      .style("stroke-width", 1.5 / scale + "px")
      .attr("fill", "red")
      .attr("transform", "translate(" + translate + ")scale(" + scale + ")")
}

function zoomout() {
  active.classed("active", false)
  active = d3.select(null)
  $("#map-path circle").css({"stroke-width": 1})
  // $("#map-path circle").css({"stroke": 'blue'})
  $("#map-path circle").css({"r": 5})
  svg.transition()
     .duration(750)
     .style("stroke-width", "1.5px")
     .attr("transform", "");
}

function broomify(data) {debugger
	

  zoomout()
  var args = G.url.parse(location.href),
      q = args.searchList;
		
  if ($("#blockId").val() != undefined && $("#blockId").val()!='0') {
	    var city = _.filter(data, function(d){ return d.DISTRICT === $("#blockId option:selected").text()});
	    
		zoombySTNM($("#stateId option:selected").text())
	    $("#map-path circle").css({"r": 1.7})
	    $("#map-path circle[data-city='"+$("#blockId option:selected").text()+"']")
	      .css({"stroke-width":0.5, "stroke": "#000"})
	  }
  else if ($("#distId").val() != undefined && $("#distId").val()!='0') {
    var city = _.filter(data, function(d){ return d.DISTRICT === $("#distId option:selected").text()});
    
	zoombySTNM($("#stateId option:selected").text())
    $("#map-path circle").css({"r": 2.5})
    $("#map-path circle[data-city='"+$("#distId option:selected").text()+"']")
      .css({"stroke-width":0.5, "stroke": "#000"})
  }
  else{
	  if ($("#stateId option:selected").text() != undefined && $("#stateId option:selected").text()!='Select') {
	    
		
		var state = _.filter(data, function(d){ return d.STATE === $("#stateId option:selected").text()});
	    var getstate=$("#stateId option:selected").text();
		zoombySTNM(getstate)
    }
  }
}

function zoombySTNM(ST_NM) {
 // console.log(ST_NM);
  d3.selectAll('#map-path path')
    .filter(function (d) {
      ST_NM = (ST_NM === 'ARUNACHAL PRADESH') ? 'ARUNANCHAL PRADESH' : ST_NM;
      return d.properties.ST_NM === ST_NM;
      // try{
      //  return d.properties.STATE_NAME.toUpperCase() === ST_NM;
      // }
      // catch(err){}
    })
    .attr('fill', '#01665E')
    .each(zoomin)
}

function draw_donut(selector, width, height, sec_color, color, font_size, data){
  var twoPi = 2 * Math.PI,
      formatPercent = d3.format(".0%");

  var arc = d3.svg.arc()
      .startAngle(0)
      .innerRadius((height / 4.0) + 5)
      .outerRadius(height / 2.0);

  var container = d3.select("body " + selector);
  container.selectAll('*').remove()

  var svg = container.append("svg")
      .attr("width", width)
      .attr("height", height)
    .append("g")
      .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

  var meter = svg.append("g")
      .attr("fill",  sec_color);

  meter.append("path")
      .attr("class", "background")
      .attr("d", arc.endAngle(twoPi));

  meter.append("path")
      .attr("fill", color)
      .attr("d", arc.endAngle(twoPi * data));

  meter.append("text")
      .attr("text-anchor", "middle")
      .attr("dy", ".35em")
      .attr('fill', '#000')
      .style('font-size', font_size)
      .style('font-weight', 'bold')
      .text(formatPercent(data));
}

$(document).on('click', '.details', function() {
  if($(".expand" ).hasClass("hideme")){
    load_insights(75, 75)
    load_deepdive(30, 30)
    $('.donutbar-1').css('width', '24%')
    $('.donutbar-2').css('width', '24%')
    $('.donutbar-3').css('width', '24%')
    $('.donutbar-4').css('width', '24%')
    $('.expand').removeClass('hideme')
  }
  else{
      $('.expand').addClass('hideme')
      $('.details').css('margin-bottom', '5px')
      load_insights(100, 100)
      $('.donutbar-1').css('width', '31%')
      $('.donutbar-2').css('width', '31%')
      $('.donutbar-3').css('width', '31%')
      $('.donutbar-4').css('width', '31%')
      load_deepdive(50, 50)
  }
})

var svgheight = 80
var svgwidth = 250

function load_insights(width, height){
	
		var areatype =$('#viewMode').val();
		var totalBlock =$('#totalBlock').val();
		var vacantCount =$('#vacantCount').val();
		var allocatedCount =$('#allocatedCount').val();
		if(areatype == 'VACANT'){
			
			draw_donut('.donutbar-' + 1, width, height,'#BABDDE', '#756BB0', '18px', vacantCount/totalBlock);
		}
		else{ 
			
			draw_donut('.donutbar-' + 2, width, height,'#BABDDE', '#756BB0', '18px',allocatedCount/totalBlock);	
		}

}

function load_deepdive(width, height){
  $.each(deepdive, function(index, data) {
    var areatype =$('#viewMode').val();
    if(areatype === undefined){areatype = 'VACANT'}
    else{ areatype = areatype }
    draw_donut('.donut-sample-' + data.RANK, width, height, data.SEC_COLOR, data.COLOR, '10px', data[areatype] / 100)
  })
}
var filtered_data = data_api;
var filtered_data1 = data_api1;
function data_filter(){
  var global_filter = $('#viewMode').val();
  var _areatype = $('#viewMode').val();
  if(_areatype === undefined){_areatype = 'VACANT'}
  else{ _areatype = _areatype }
  if(_areatype === 'VACANT'){
  filtered_data = _.filter(data_api, function(d){ return d.CATEGORY === _areatype; });
  }
  if(_areatype === 'ALLOCATED'){
  filtered_data1 = _.filter(data_api1, function(d){ return d.CATEGORY === _areatype; });
  filtered_data=filtered_data1;
  }
 
	return filtered_data;
  
}

function redraw(){
  var global_filter = G.url.parse(location.href).searchList;
  var _areatype = $('#viewMode').val();
  var plot_type = 'nondist';
  var selected_city = '';
  var get_states = $("#stateId option:selected").text();
  var _get_dist = $("#distId option:selected").text();
  var ranklists = {'ALLOCATED': ['1-30000'],
                   'VACANT': ['1-30000']};
  var selected_range = global_filter['range'];
  var map_area = {'VACANT': '1-30000', 'ALLOCATED': '1-30000'}
  // var area_btn = {'VACANT': 'VACANT', 'ALLOCATED': 'ALLOCATED'}
  var nodist = 1;
 // if($("#distId option:selected").text()){nodist = 0}
  if(_areatype === undefined){
    _areatype = 'VACANT'
  }
  else{
    _areatype = _areatype
  }

  filtered_data = data_filter();
  var states = [];
  var districs = [];
  var data_to_dropdowns = [];
  var selected_state = '';
  var selected_dist_drop = '';

  _.each(filtered_data, function(d){
    states.push(d.STATE);
  })

  if(_get_dist != undefined){selected_dist_drop = _get_dist}
  else{selected_dist_drop = 'Select District'}

  if(get_states != undefined){
    selected_state = get_states;
    data_to_dropdowns = _.filter(filtered_data, function(d){ return d.STATE === selected_state; })
  }
  else{
    selected_state = 'Select State';
    data_to_dropdowns = filtered_data;
  }
  _.each(data_to_dropdowns, function(d){
    districs.push(d.DISTRICT + '====' + d.RANGE + '====' + d.STATE);
  })
  var dropdown_lists = {'State': _.sortBy(_.uniq(states), function (name) {return name}), 'City': _.sortBy(_.uniq(districs), function (name) {return name})}
  var btn_list = [];
  if($("#stateId option:selected").text() != undefined){
    selected_city = $("#distId option:selected").text();
    plot_type = 'dist';
    btn_list = [];
  }
  else{btn_list = ['Vacant', 'Allocated']}

  if(global_filter['range'] === undefined){selected_range = map_area[_areatype];}
  else{
    selected_range = global_filter['range'][0];
    if(ranklists[_areatype].indexOf(selected_range) < 0){
      selected_range = map_area[_areatype];
    }
  }

  var cleanness_table_data = _.filter(filtered_data, function(d){ return d.RANGE === selected_range; })
  var dist_data = _.filter(cleanness_table_data, function(d){ return d.DISTRICT === selected_city; })
  
  if(dist_data.length === 0){dist_data = cleanness_table_data[0];}
  else{dist_data = dist_data[0]}
  if(dist_data!== undefined && dist_data!=""){
  var selected_dist = dist_data.DISTRICT;
  }

  $("script#dropdown-selection").template({areatype: _areatype, selected_dist: selected_dist_drop, selected_state: selected_state, data: dropdown_lists, btn_list: btn_list})
  $('script#ranklist').template({filtered_data: filtered_data, top_four_insights: top_four_insights,
    svgheight: svgheight, svgwidth: svgwidth, deepdive: deepdive, dist_data: dist_data,
    selected_dist: selected_dist, ranklists: ranklists[_areatype], selected_range: selected_range,
    cleanness_table_data: cleanness_table_data, type: plot_type, areatype: _areatype})
  var get_dist = 'anjaw';
  if($("#distId").val()!= undefined && $("#distId").val() != "0"){
    get_dist = $("#distId option:selected").text();
  }
  var images = [];

  try{images = image_dict['assets/images/' + get_dist.replace(/ /g, '_').toLowerCase()];}
  catch(err){images = []}

  $('script#slider').template({default_page_vids: default_page_vids, nodist: nodist,
    selected_dist: get_dist, images: images})

  load_insights(100, 100);
  load_deepdive(40, 40);
 // create_datatable();
  mapify();
  ie_setup();
  slide_carousel();
  videos_control();
  load_carousel();
  lazy_load();
  default_lazy_load();
  $('.fitit').css('width', '140px');
}

function create_datatable(){
  $('.borderless').dataTable( {
    "paginate": false,
    "bFilter":false,
    "info":false,
    "scrollY":430,
    "bSort": false
  })
}

function slide_carousel(){
  $('.carousel#img_slide .item').each(function(){
    var next = $(this).next();
    if (!next.length) {
      next = $(this).siblings(':first');
    }
    next.children(':first-child').clone().appendTo($(this));

    if (next.next().length>0) {
      next.next().children(':first-child').clone().appendTo($(this));
    }
    else {
      $(this).siblings(':first').children(':first-child').clone().appendTo($(this));
    }
  })
}


redraw();
function ie_setup(){
  var is_firefox = navigator.userAgent.indexOf("Firefox");
  var is_ie = document.documentMode;
  if(is_ie){
    $('#map-svg').attr('width', '430px')
    $('#map-svg').attr('height', '500px')
    $('#score-board').attr('width', '710px')
    $('#score-board').attr('height', '50px')
  }
  if(is_firefox > -1){
    $('.caret').css('margin-top', '-6%')
  }
}

function videos_control(){
 $('video').on('play', function () {
    $("#vid_carousel").carousel('pause');
  })

  $('video').on('stop pause ended', function () {
      $("#vid_carousel").carousel();
  });

  $('.carousel-control').on('click', function(){
    $('video').each(function(){
      $(this)[0].pause()
    })
  })
}

$('.collapse').on('click', function(){
  var params = G.url.parse(location.href);
  var _keys = _.keys(params.searchList);
  var updated_uri = {};
  _.each(_keys, function(k){
    updated_uri[k] = null;
  })
  params.update(updated_uri);
  history.pushState({}, '', params.toString())
})

$('body')
  .tooltip({
    selector: '[title]',
    container: 'body',
    animation: true,
    html: true
  })
  .urlfilter({
  selector: '.urlfilter',
  target: 'pushState'
  })
  .on("loaded.g.urlfilter", redraw);

function load_carousel(){
  $('#vid_carousel').carousel({
    interval: 4000,
    cycle: true
  });
  $('#img_slide').carousel({
    interval: 5000,
    cycle: true
  });
}

function default_lazy_load(){
  $.each($('#img_slide .item.active img'), function(){
    $(this).attr("src", $(this).attr("data-original"));
    $(this).removeAttr("data-original");
  })
}

function lazy_load(){
  $('#vid_carousel').bind('slide.bs.carousel', function (e) {
    $.each($(e.relatedTarget).find('img'), function(){
      $(this).attr("src", $(this).attr("data-original"));
      $(this).removeAttr("data-original");
    });
  });

  $('#img_slide').bind('slide.bs.carousel', function (e) {
    // var slideFrom = $(this).find('.active').index();
    $.each($(e.relatedTarget).find('img'), function(){
      $(this).attr("src", $(this).attr("data-original"));
      $(this).removeAttr("data-original");
    });
  });
  // $("img.lazy").lazyload({
  //   container: $(".scroller"),
  //   effect : "fadeIn",
  //   threshold : 0
  // })
}

$(window).on('popstate', redraw)
$('body')
  .on('click', '.video:not(.first)', function(e) {
    e.preventDefault()
    var url = $(this).attr('data-q')
    $('.full-video')
      .html('<div class="modal-footer"><button type="button" data-dismiss="modal">X</button></div><iframe src="' + url + '" frameborder="0" allowfullscreen></iframe>')
      .show()
  })
  .on('click', '.full-video', function() {
    $(this).hide().find('iframe').remove()
  })