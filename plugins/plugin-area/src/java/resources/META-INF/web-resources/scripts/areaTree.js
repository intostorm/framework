/**
 * $.fn.areaTree
 * Edit by ZYB
 * param :{
 * 	label : 'id',	- 获取地区名称的DOM元素ID
 *  maxLevel : 'number', - 省:1,市:2,县:3....设置最大选择级别
 *  selectLevel : 'number'	- 省:1,市:2,县:3....设置地区可选级别
 *  alignTo : 'id',	- 对象根据那个DOM对其(在显示Tree的时候,Tree对其的元素,默认根据INPUT对齐)
 *  noParent : 'boolean',	- 是否可以选择上级元素,默认false
 * }
 */
;(function() {
	var cp = window.contextPath ? window.contextPath : '';
	function getSetting() {
		return {
			view : {
				selectedMulti : false,
				addDiyDom : function(treeId, tNode) {
					var $tree = $.fn.zTree.getZTreeObj(treeId);
					if ($tree.noParent == true) {
						return;
					}

					var pTId = tNode.parentTId;
					if (pTId == null && tNode.id.length > 2) {// 根节点,且非省级节点
						var $a = $('#' + tNode.tId + '_a');
						var $span = $(
								'<span class="button ico_docu" title="上级"></span>')
								.css({
									"backgroundPosition" : "-144px -16px"
								});
						$a.after($span);
						var treeObj = $.fn.zTree.getZTreeObj(treeId);
						$span.click(function(e) {
								/*
								 * tNode.isAjaxing = true;
								 * treeObj.updateNode(tNode);
								 */
								$.post(
										cp + "/plugin/areaTree?m=getParentNode",
										{ "areaCode" : tNode.id }, 
										function(data, textStatus, xhr) {
											if (data && (data.success == true)) {
												var d = data.data;
												var nodes = filter(treeId, null, data);
												nodes.zAsync = true;
												nodes = treeObj.addNodes(null, nodes, true);
												nodes = nodes[0];
												var child = nodes.children.length > 0 ? nodes.children[0] : false;
												nodes.zAsync = true;
												tNode.isAjaxing = false;
												if (child) {// 如果有子节点,则将置为第一个子节点
													treeObj.moveNode(child, tNode, 'prev');
												} else {// 否则将添加到父节点中
													treeObj.moveNode(nodes, tNode, 'inner');
												}
												treeObj.expandNode(nodes, true);
												$span.remove();
											} else {
												alert(data ? data.message : '上级地区数据请求发生错误!');
											}
										}, 'json');
							});
					}
				}
			},

			async : {
				enable : true,
				url : cp + "/plugin/areaTree?m=loadNodes",
				autoParam : [ "id=areaCode", "name=areaName", "level" ],
				otherParam : {
					"otherParam" : "zTreeAsyncTest"
				},
				dataFilter : filter
			},

			callback : {
				"onClick" : onClick,
				"beforeAsync" : beforeAsync,
				"onAsyncSuccess" : onAsyncSuccess
			}
		};
	}
	;
	function getLevelLength(maxLevel) {
		var maxLen = 12;
		switch (maxLevel) {
		case 1:
			maxLen = 2;
			break;
		case 2:
			maxLen = 4;
			break;
		case 3:
			maxLen = 6;
			break;
		case 4:
			maxLen = 9;
			break;
		case 5:
			maxLen = 12;
			break;
		case 6:
			maxLen = 15;
			break;
		default:
			maxLen = 12;
			break;
		}
		return maxLen;
	}
	;
	function beforeAsync(treeId, treeNode) {
		return true;
	}
	;
	function filter(treeId, parentNode, childNodes) {
		
		var $tree = $.fn.zTree.getZTreeObj(treeId);
		var maxLevel = $tree.maxLevel;
		if (!maxLevel) {
			maxLevel = 5;
		}
		var maxLen = getLevelLength(maxLevel);

		if (!childNodes.success)
			return [];
		if(childNodes.data.length == 0){
			return [];
		}
		var nodes = [];
		for ( var i = 0, l = childNodes.data.length; i < l; i++) {
			var obj = $.extend({}, childNodes.data[i]);
			if (childNodes.data[i].areacode.length > maxLen) {
				continue;
			} else {
				nodes.push(obj);
			}
			obj.name = childNodes.data[i].areaName;
			obj.id = childNodes.data[i].areacode;
			obj.isParent = childNodes.data[i].areacode.length == maxLen ? false
					: true;
		}
		if (!parentNode) {
			var node = nodes[0];
			nodes = nodes.slice(1);
			node.children = nodes;
			node.open = true;
			nodes = node;
		} else {
			nodes = nodes.slice(1);
		}
		return nodes;
	}
	function onClick(event) {
		event.stopPropagation();
		event.preventDefault();
	}
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		try {
			delete treeObj.setting.async.otherParam.areaCode;
		} catch (e) {
		}
	}
	var id = 0;
	function generateNewId() {
		return "AREA_TREE_" + (id++);
	}
	;
	$.fn.areaTree = function(param) {
		var $me = this;
		param = param ? param : {};
		$me.attr('readOnly', true);
		var id = $me.attr('id');
		var dId = "zdialog_" + id, tId = "ztree_" + id;
		var $treeObj = $.fn.zTree.getZTreeObj(tId), $dialogDiv = $('#' + dId);
		if (!$treeObj) {
			$dialogDiv = $(
					'<div style="display:none; z-index:99999999; position: absolute; background:#F0F6E4; overflow:auto; maxHeight:400px;"></div>')
					.attr('id', dId);
			$dialogDiv.css('minWidth', $me.outerWidth() + 'px');
			if ($.browser.msie) {
				$dialogDiv.css('height', 300 + 'px');
			} else {
				$dialogDiv.css('maxHeight', 300 + 'px');
			}
			/*
			 * $dialogDiv.bind('mouseleave.areaTree', function() {
			 * $dialogDiv.fadeOut("fast"); });
			 */
			var $tree = $('<ul class="ztree"></ul>').attr('id', tId).appendTo(
					$dialogDiv);
			$dialogDiv.appendTo(document.body);
			var setting = getSetting();
			if(param && param.selectLevel){
				param.maxLevel = param.selectLevel;				
			}
			setting.callback.onClick = function(event, treeId, treeNode,
					clickFlag) {
				var $tree = $.fn.zTree.getZTreeObj(treeId);
				if(param && param.selectLevel){
					var len = getLevelLength(param.selectLevel);
					if(treeNode.id.length != len){
						$tree.expandNode(treeNode, true, false, true);
						return false;
					}
				}
				if (param && param.label) {
					var $label = $('#' + param.label);
					if ($label.length > 0) {
						if ($label.is('input')) {
							$label.val(treeNode.name);
						} else {
							$label.text(treeNode.name);
						}
					}
				}
				$me.val(treeNode.id);
				$me.triggerHandler('change', [ tId, treeNode ]);
			};
			var areaCode = $me.val();
			if ('maxLevel' in param) {
				var len = getLevelLength(param.maxLevel);
				areaCode = areaCode.length >= len ? areaCode.substring(0, len)
						: areaCode;
			} else {
				param.maxLevel = 5;
				var len = getLevelLength(5);
				areaCode = areaCode.length >= len ? areaCode.substring(0, len)
						: areaCode;
			}
			setting.async.otherParam = {
				'areaCode' : areaCode,
				'maxLevel' : param.maxLevel
			};
			$treeObj = $.fn.zTree.init($tree, setting);
			$treeObj.maxLevel = param.maxLevel;
			$treeObj.noParent = param.noParent;
		}
		var $alignTo = param.alignTo ? $(param.alignTo) : $me;
		var offset = $alignTo.offset(), outerHeight = $alignTo.outerHeight(), outerWidth = $alignTo
				.outerWidth();
		$dialogDiv.css({
			left : offset.left + 'px',
			top : (offset.top + outerHeight) + 'px'
		}).slideDown("fast");
		var eventName = 'click.' + dId;
		$(document.body).unbind(eventName);
		setTimeout(function() {
			$(document.body).bind(
					eventName,
					function(event) {
						var $target = $(event.target), $p = $target.parents('#'
								+ dId);
						if ($target.attr('id') == id || $target.is('#' + dId)
								|| $p.length > 0) {
							return;
						} else {
							$(document.body).unbind(eventName);
							$dialogDiv.fadeOut("fast");
						}
					});
		}, 100);
	};
})();