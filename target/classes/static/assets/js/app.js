$(function() {
    // 读取body data-type 判断是哪个页面然后执行相应页面方法，方法在下面。
  
    //     // 判断用户是否已有自己选择的模板风格
    //    if(storageLoad('SelcetColor')){
    //      $('body').attr('class',storageLoad('SelcetColor').Color)
    //    }else{
    //        storageSave(saveSelectColor);
    //        $('body').attr('class','theme-black')
    //    }

    autoLeftNav();
   // autoMidNav();
   
   $(window).resize(function() {
        autoLeftNav();
        //autoMidNav();
        console.log($(window).width())
    });

    //    if(storageLoad('SelcetColor')){

    //     }else{
    //       storageSave(saveSelectColor);
    //     }
})


//侧边菜单开关


function autoLeftNav() {
 $('.tpl-header-switch-button').on('click', function() {
	 
     if ($('.left-sidebar').is('.active')) {
         if ($(window).width() > 1024) {
             $('.tpl-content-wrapper').removeClass('active');
            // $('.mid-sidebar').removeClass('active');
         }
         $('.left-sidebar').removeClass('active');
     } else {

         $('.left-sidebar').addClass('active');
         if ($(window).width() > 1024) {
             $('.tpl-content-wrapper').addClass('active');
             //$('.mid-sidebar').addClass('active');
         }
     }
 });

/* if ($(window).width() < 1024) {
     $('.left-sidebar').addClass('active');
 } else {
     $('.left-sidebar').removeClass('active');
 }*/
}



//侧边菜单
$('.sidebar-nav-sub-title').on('click', function() {
 $(this).siblings('.sidebar-nav-sub').slideToggle(80)
     .end()
     .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
})

//中间栏
 $("#drawDistrict").on("click" , function (){
	 
	 if ($(".panelWrap").is('.isshow')) {
		 $(".panelWrap").children().hide();
         $(".panelWrap").removeClass('isshow');
         $("#map1_container").removeClass('isshow');
     } else {
    	 $(".panelWrap").addClass('isshow');
    	 $(".panelWrap").children().show();
         $("#map1_container").addClass('isshow');
         

     }
 });
    
//新建图层，panelWrap上新建div操作图层，地图上新建按钮
$("#drawDiscrictButton").on("click" , function (){
	var newLayer={
			name: "",
			layerDate: []  //图层数据
	};   //图层对像
	var styleOptions = {
	        strokeColor:"red",    //边线颜色。
	        fillColor:"",      //填充颜色。当参数为空时，圆形将没有填充效果。
	        strokeWeight: 3,       //边线的宽度，以像素为单位。
	        strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
	        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
	        strokeStyle: 'solid' //边线的样式，solid或dashed。
   	    }
   		 
   		 //实例化鼠标绘制工具
	var drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: false, //是否开启绘制模式
        drawingType: BMAP_DRAWING_POLYGON,
         //绘制工具栏的选择
        enableDrawingTool: true, //是否显示工具栏
        drawingToolOptions: {
            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
            offset: new BMap.Size(5, 10), //偏离值
            scale: 0.8 ,//工具栏缩放比例
            drawingTypes : [
            //BMAP_DRAWING_MARKER,
            //BMAP_DRAWING_CIRCLE,
           // BMAP_DRAWING_POLYLINE,
            BMAP_DRAWING_POLYGON,
            //BMAP_DRAWING_RECTANGLE 
     		],
     		drawingModes:[BMAP_DRAWING_POLYGON]
        },
        //circleOptions: styleOptions, //圆的样式
       // polylineOptions: styleOptions, //线的样式
        polygonOptions: styleOptions, //多边形的样式
        //rectangleOptions: styleOptions //矩形的样式
	 }); 
		
	 drawingManager.addEventListener('overlaycomplete', function(e){
		
	     var grid={};    
     	 grid.overlay = e.overlay;
     	 grid.gridName = "";
     	 grid.gridMana = "";
     	 newLayer.layerDate.push(grid);
        var isPoly = false;
        if (e.drawingMode == BMAP_DRAWING_MARKER) {
           // result += ' 坐标：' + e.overlay.getPosition().lng + ',' + e.overlay.getPosition().lat;
        }
        if (e.drawingMode == BMAP_DRAWING_CIRCLE) {
            //result += ' 半径：' + e.overlay.getRadius();
            //result += ' 中心点：' + e.overlay.getCenter().lng + "," + e.overlay.getCenter().lat;
        }
        if (e.drawingMode == BMAP_DRAWING_POLYLINE || e.drawingMode == BMAP_DRAWING_POLYGON || e.drawingMode == BMAP_DRAWING_RECTANGLE) {
           /* result += ' 所画的点个数：' + e.overlay.getPath().length  +" <br/>";
            for(var j = 0; j < e.overlay.getPath().length; j++){
                var onepoint =e.overlay.getPath()[j];
                result += onepoint.lng+","+onepoint.lat+");<br/>";
            }*/
            if( e.drawingMode == BMAP_DRAWING_POLYGON){
            isPoly = true;
            }
            //e.overlay.addEventListener("mouseover", function(){allert("进入此区域");});
            
        }
       if(isPoly){
        popBox(grid);
        }  
	 });
	 mapLayers.push(newLayer);
});


//获取图层信息
function getLayer(layerSeq){
	var overlays = mapLayers[layerSeq - 1].layerDate;
     var multiPoly = new PolygonConection(); 
     for(var i = 0; i < overlays.length; i++){
     	var coordinatesOfPoly = [];
     	var properties ={};
     	properties.gridName = overlays[i].gridName;
     	properties.gridMana = overlays[i].gridMana;
         var overlay=overlays[i].overlay.getPath();
         for(var j = 0; j < overlay.length; j++){
             var grid =overlay[j];
             var onepoint = [];
             onepoint.push(grid.lng);
             onepoint.push(grid.lat);
             coordinatesOfPoly.push(onepoint);
         }
         var psgeo = new polygonString();  //多边形的GEOjson
         psgeo.setCoordinates(coordinatesOfPoly); //多边形添加坐标
         psgeo.setProperties(properties);
         multiPoly.addPolygon(psgeo);     //几何集合的GEOJSON添加该多边形
       	
     }
     return JSON.stringify(multiPoly);
 }

   //生成多边形的GEOjson
	   function polygonString(){
	   		this.type = "Polygon";
	   		this.coordinates = [];
	   		this.properties = {};
	   		this.setCoordinates= function(coordinates){
	   			this.coordinates = coordinates;
	   		};
	   		this.setProperties = function(porperties){
	   			this.properties = porperties;
	   		}
	   		
	   		
	   }
	   
	     //生成初始化的多边形集合的GEOjson
	   function PolygonConection(){
	   		this.type = "GeometryCollection";
	   		this.geometries = [];
	   		this.properties = {};
	   		this.addPolygon = function(polygon){
	   			this.geometries.push(polygon);
	   			
	   		};
	   }

	   
	   /*点击弹出按钮*/
	    function popBox(grid) {
	    	
	        var popBox = document.getElementById("popBox");
	       // var popLayer = document.getElementById("popLayer");
	        popBox.style.display = "block";
	        $('#reset').on('click', function() {
		    	var gridName = $(this).parent().siblings().children('input');
		    	gridName.val("");
		    			//.children('input').lenght) ;
		    }
		    );
	        $('#submit').on('click', function() {
		    	//var gridName = $(this).parent().siblings().children('input');
	        	 grid.gridName = $('#gridname').val();
	  		   
	  		     grid.gridMana = $('#gridmana').val();
	        	closeBox();
		    			//.children('input').lenght) ;
		    }
		    );
	        //popLayer.style.display = "block";
	    };
	 
	    /*点击关闭按钮*/
	    function closeBox() {
	        var popBox = document.getElementById("popBox");
	        //var popLayer = document.getElementById("popLayer");
	        popBox.style.display = "none";
	        //popLayer.style.display = "none";
	    }
	    
	
	    
	   
	    
	 
	    