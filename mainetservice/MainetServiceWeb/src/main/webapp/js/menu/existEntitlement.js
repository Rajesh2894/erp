$(document).ready(function() {

	

	$("#tree1").dynatree({
		checkbox : true,
		selectMode : 3,
		autoCollapse : false,
		onSelect : function(select, node) {
			var selKeys2 = $.map(node.tree.getSelectedNodes(), function(node) {
				return node.data.target;
			});

			var partsel2 = new Array();
			$(".dynatree-partsel:not(.dynatree-selected)").each(function() {
				var node = $.ui.dynatree.getNode(this);
				partsel2.push(node.data.target);
			});
			selKeys2 = selKeys2.concat(partsel2);

			$("#menuIds").val(selKeys2);

		},
		onActivate : function(node) {
			node.select(true);

		},

	});

});
