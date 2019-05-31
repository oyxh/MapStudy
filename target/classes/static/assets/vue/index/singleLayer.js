//图层组件,父组件为layerItem,用于生成各个图层及操作
        var singleLayer = {
  			props:['layerChange','newLayer'],
  			data:function(){
            	return {
            		layersget:[{layerId:null,layerName:null,layerData:null,userId:null}],
                    overlaysInLayers:[],  //各格图层的覆盖物集合，生成一个添加一个。   
                     activeLayer:0,
                      styleObjectItemAll:{
                        color: '#868E8E',
					    fontSize: '13px',
					    height:'38px',
                        background:'#D8D8D8',
                        width:'100%',
                        float:'right',
                        textAlign:'left',
                         lineHeight:'38px',
                        display:'inline-block',
                        float:"left"
                      },
                      buttonLeft:{
                        width:'50%',
                        float:'left',
                        border:'2px solid #eee'
                      },
                      buttonRight:{
                        width:'50%',
                        float:'right',
                        border:'2px solid #eee'
                      },
                      closeButton:{
                      	float:'right'
                      },
                       checkeButton:{
                      	marginLeft:'15px'
                      },
                      styleOptions:{  //绘制图形的式样
                      	strokeColor:"red",    //边线颜色。
				        fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。
				        strokeWeight: 2,       //边线的宽度，以像素为单位。
				        strokeOpacity: 0.8,       //边线透明度，取值范围0 - 1。
				        fillOpacity: 0.1,      //填充的透明度，取值范围0 - 1。
				        strokeStyle: 'solid' //边线的样式，solid或dashed。
                      },
                      drawTool:null                
            	}
            },
  			template: `
  				<div >
                  <div   v-for="(layer,index) in this.layersget" :style= "{height:'100%',display:'inline-block',
                  border: index === activeLayer ? '2px solid blue' : '2px solid #66b3FF'}"
                  @click=clickSingleLayer($event,layer.layerName,index) >
                        <div v-bind:style="styleObjectItemAll">
                        	<label>图层名称:{{layer.layerName}}- {{ index }}</label>
                             
                                
                                <label :style="checkeButton">
                                	 关闭编辑
                                </label>
                                <input  type="checkbox" value="checked" >
                                
                        	<button type="button" class="am-close"  :style="closeButton" @click=deleteLayer($event,layer.layerId,index)>&times;</button>
                                 
                              
                        </div> 
                        <div> 
                        <button type="button" class="am-btn am-btn-secondary am-radius" :style="buttonLeft" @click=drawLayer>
                        <i class="am-icon-pencil"></i>图层绘制</button>  
                        <button type="button" class="am-btn am-btn-secondary am-radius" :style="buttonRight">
                        <i class="am-icon-trash"></i>清除图层</button>
                        <button type="button" class="am-btn am-btn-secondary am-radius" :style="buttonLeft">
                        <i class="am-icon-upload"></i>导入数据</button>  
                        <button type="button" class="am-btn am-btn-secondary am-radius" :style="buttonRight">
                        <i class="am-icon-eye"></i>隐藏图层</button>
                        </div>   
                                       
                        <div style="clear: both;display: none;"></div>

  				</div>
  					<div style="visibility: hidden">{{getmsg}}</div>	<!--计算属性执行，函数getmsg返回值为layerchange,该值变化就会执行该函数	-->	
  			</div>
  			`,
  			computed:{
  				
				getmsg:function(){   //获取图层的请求
				var that = this;
				var postconfig={
	                   method: 'get',
	                   url:'layerlist' 
	               };
	          
		 		 axios(postconfig)
	               .then(
	               	function (response) {
	               		 console.log(response);
					      that.layersget = response.data;
					      that.initOverlays();// 初始化图层
					      }
	                                
	               )
	               .catch(function (error) {
	                   console.log(error);
	               
                   });  //axios
                   
	               return that.layerChange;
                }
                
			},
			methods:{
				clickSingleLayer(e,layerName,index){
                    this.activeLayer = index;
				},
				getDistancePointTOLine(x,y,x1,y1,x2,y2){//x,y为点的位置,矢量法
					var pointObject={
						point:[],
						dis:0.0
					};
					 var cross = (x2 - x1) * (x - x1) + (y2 - y1) * (y - y1);
					if (cross <= 0) {
						pointObject.point.put(y1);
						pointObject.point.put(x1);
						pointObject.dis = Math.pow((x - x1) * (x - x1) + (y - y1) * (y - y1),0.5);
						return pointObject;
					}									  
					var d2 = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
					if (cross >= d2){
						pointObject.point.put(y2);
						pointObject.point.put(x2);
						pointObject.dis = Math.pow((x - x2) * (x - x2) + (y - y2) * (y - y2),0.5);
						return pointObject;
					}				  
					var r = cross / d2;
					var px = x1 + (x2 - x1) * r;
					var py = y1 + (y2 - y1) * r;
					pointObject.point.put(py);
					pointObject.point.put(px);
					pointObject.dis =Math.pow((x - px) * (x - px) + (py - y) * (py - y),0.5);
					return pointObject;					
				},
				getMinDistiance(flag){ //获取点最近的多边形覆盖物距离上的点 flag为true,全部图层，否则为活动图层
					
				},
				initOverlays(){//初始化图层
					this.overlaysInLayers=[];
					for(var i=0;i<this.layersget.length;i++){
						var overlays=[];    //layersget的数据中生成
						var overlaysMarker =[];
						var overlaysPolygon=[];
						overlays.push(overlaysMarker);
						overlays.push(overlaysPolygon);
						this.overlaysInLayers.push(overlays);
					}
					
				},
				
				deleteLayer(e,layerId,index){   //删除图层
					var that = this;
					this.overlaysInLayers.splice(index,1);
					layui.use(['layer', 'form'], function(){
                    var layer = layui.layer
                    ,form = layui.form;
					   layer.confirm('确定要删除选中的记录？', {
							btn : [ '确定', '取消' ]
						}, function(index) {
							layer.close(index);
							var postconfig={
                                method: 'post',
                                url:'removelayer',
                                data:{
                                    id:layerId
                                },
                                transformRequest: [function (data) { //登录时处理数据格式,处理后后台接收的参数为data按顺序传递
                                  let ret = ''
                                  for (let it in data) {
                                   ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
                                  }
                                  return ret;
                              }]
                            };
                       	   axios(postconfig)
                            .then(
                            
                                function (response) {
                                
                              	that.changeSuccess();
                                console.log(response);
                                
                                
                            })
                            .catch(function (error) {
                                console.log(error);
                            
                            });  //axios
							
						}); //layerconfirm
					   
					   
						
					});//layeruse
					
				},
				changeSuccess(){   //与父组件通信，告诉父组件图层变化。
				
					this.$emit('layerwatch');   //图层发生变化，
					
                },
              	generateDrawTool(){
              		if (this.drawTool == null) {
						var drawingManager = new BMapLib.DrawingManager(map, {
				       isOpen: true, //是否开启绘制模式
				        enableDrawingTool: true, //是否显示工具栏
				        //drawingMode:BMAP_DRAWING_POLYGON,//绘制模式  多边形
				        drawingToolOptions: {
				            anchor: BMAP_ANCHOR_TOP_LEFT, //位置
				            offset: new BMap.Size(20, 5), //偏离值
				            scale:0.8, //缩放
				            drawingModes:[
				            	BMAP_DRAWING_MARKER,
				                BMAP_DRAWING_POLYGON,
				                BMAP_DRAWING_RECTANGLE
				            ]
				        },
				        circleOptions: this.styleOptions, //圆的样式
				        polylineOptions: this.styleOptions, //线的样式
				        polygonOptions: this.styleOptions, //多边形的样式
				        rectangleOptions: this.styleOptions //矩形的样式
				    	});
				    	this.drawTool=drawingManager;
				    	this.drawTool.removeEventListener("add");
						this.drawTool.addEventListener('overlaycomplete', this.overlaycomplete,"add");
					}    	
				},
				generateOverlays(){  //新建图层生成图层的覆盖物集合，在overlaysInLayers的上面新增一个放在最前面
					var overlays=[];
					
					var overlaysMarker =[];
					var overlaysPolygon=[];
					overlays.push(overlaysMarker);
					overlays.push(overlaysPolygon);
					
					this.overlaysInLayers.unshift(overlays);
					return overlays;
				},
				overlaycomplete(e){
					//alert("overlaycomplete");
					
					var overlays = this.overlaysInLayers[this.activeLayer];
					var overlaysMarker = overlays[0];
					var overlayspolygon = overlays[1];
					if(e.drawingMode==BMAP_DRAWING_MARKER){
						
						overlaysMarker.push(e.overlay);
					}else{
						overlayspolygon.push(e.overlay);
						//e.overlay.setStrokeWeight(5);
					}
				      // overlays.push(e.overlay);
				       
				     /*  var path = e.overlay.getPath();//Array<Point> 返回多边型的点数组
				        for(var i=0;i<path.length;i++){
				        	var pixelpoint=map.pointToPixel(path[i]);
				            console.log("lng:"+path[i].lng+"\n lat:"+path[i].lat+"\n pixelx:"+pixelpoint.x+
				            "\n pixely:"+pixelpoint.y);
				        }*/
				},
				drawLayer(){
				
					
					this.generateDrawTool();
					
					
				}
				
			
				
            },
            watch:{
                newLayer:function(newVal,oldVal){  //newLayer发生变化，父组件的新建图层按钮传来新建图层的信息，活跃图层变为0,执行新建图层
                   this.activeLayer = 0; 
                   this.generateOverlays();
                   this.drawLayer();
                }
            }
							
          }   ;
          
