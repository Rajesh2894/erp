/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	
	// Define changes to default configuration here. For example:
	// config.language = 'fr';

	// config.uiColor = '#AADC6E';
        config.skin = 'bootstrapck';
        
	// Add HTML% VIDEO PLUGIN.
	config.extraPlugins = 'html5video';
    config.allowedContent = true;
    config.protectedSource.push( /<i class[\s\S]*?\>/g );
    config.protectedSource.push( /<\/i>/g );
};
