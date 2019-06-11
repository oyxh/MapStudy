var oyxhLib = window.oyxhLib = oyxhLib || {};
(function() {
	
	var MyControl = oyxhLib.MyControl = function(map){
		console.log("MyControl");
		 if (!map) {
                return;
            }
            instances.push(this);    
            this._initialize(map);
	}
	MyControl.prototype._initialize = function(map) {
		console.log("MyControl _initialize");
		 var myControlTool  = new MyControlTool();
            this._myControlTool = myControlTool;
            map.addControl(myControlTool);
	}
	
	var MyControlTool = function(){
		console.log("MyControlTool");
		  this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
        this.defaultOffset = new BMap.Size(10, 10);
      };
      MyControlTool.prototype = new BMap.Control();
      
      MyControlTool.prototype.initialize = function(map) {
		console.log("initialize");
     	// 创建一个DOM元素
	  var div = document.createElement("div");
	  // 添加文字说明
	  div.appendChild(document.createTextNode("选择地区"));
	  // 设置样式
	  div.style.cursor = "pointer";
	  div.style.border = "1px solid gray";
	  div.style.width = "60px";
	   div.style.height = "28px";
	  div.style.backgroundColor = "white";
	  // 绑定事件,点击一次放大两级
	  div.onclick = function(e){
	  	 getCounty();
		 //map.setZoom(map.getZoom() + 2);
	  }
	  // 添加DOM元素到地图中
	  map.getContainer().appendChild(div);
	  // 将DOM元素返回
	  return div;
        
    }
    
    var getCounty = function(){
    	layer.open({
		  type: 2,
		  title: 'layer mobile页',
		  shadeClose: true,
		  shade: 0.8,
		  area: ['380px', '90%'],
		  content: 'assets/vue/index/county.html' //iframe的url
		});
    }
    
    
 
        
    var instances =[];
	
	
})();