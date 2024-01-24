/*
 * L.SingleTile uses L.ImageOverlay to display a single-tile WMS layer.
 * url parameter must accept WMS-style width, height and bbox.
 */

L.SingleTile = L.ImageOverlay.extend({
	defaultWmsParams: {
		service: 'WMS',
		request: 'GetMap',
		version: '1.1.1',
		layers: 'ward_boundary,revenue_boundary',
		styles: '',
		srs: 'EPSG:4326',
		format: 'image/png',
		transparent: false
	},

	initialize: function( url, options ) {
		this.wmsParams = L.extend({}, this.defaultWmsParams);
		L.ImageOverlay.prototype.initialize.call(this, url, null, options);
	},

	setParams: function (params) {
		L.extend(this.wmsParams, params);
		return this;
	},

	redraw: function () {
		this._updateImageUrl();
	},

	onAdd: function (map) {
		var projectionKey = parseFloat(this.wmsParams.version) >= 1.3 ? 'crs' : 'srs';
//		this.wmsParams[projectionKey] = 'EPSG:4326'; // this is incorrect!
		//this.wmsParams[projectionKey] = map.options.crs.code;
		L.ImageOverlay.prototype.onAdd.call(this, map);
		map.on('moveend', this._updateImageUrl, this);
	},

	onRemove: function (map) {
		map.on('moveend', this._updateImageUrl, this);
		L.ImageOverlay.prototype.onRemove.call(this, map);
	},

	// Copypasted from L.ImageOverlay (dirty hack)
	_initImage: function () {
		this._image = L.DomUtil.create('img', 'leaflet-image-layer');

		if (this._map.options.zoomAnimation && L.Browser.any3d) {
			L.DomUtil.addClass(this._image, 'leaflet-zoom-animated');
		} else {
			L.DomUtil.addClass(this._image, 'leaflet-zoom-hide');
		}

		this._updateOpacity();
		this._bounds = this._map.getBounds();

		//TODO createImage util method to remove duplication
		L.extend(this._image, {
			galleryimg: 'no',
			onselectstart: L.Util.falseFn,
			onmousemove: L.Util.falseFn,
			onload: L.bind(this._onImageLoad, this),
			src: this._constructUrl()
		});
	},

	_onImageLoad: function () {
		this._bounds = this._map.getBounds();
		this._reset();
		this.fire('load');
	},

	_updateImageUrl: function () {
		this._image.src = this._constructUrl();
	},

	_constructUrl: function () {
		var size = this._map.getSize();
		var b = this._map.getBounds();
		return this._url + L.Util.getParamString(this.wmsParams, this._url) + "&width=" + size.x + "&height=" + size.y + "&bbox=" + b.toBBoxString();
	}
});

L.singleTile = function (url, options) {
	return new L.SingleTile(url, options);
};
