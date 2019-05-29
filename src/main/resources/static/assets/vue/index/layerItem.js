  //图层内容有子组件singleLayer,父组件为index页面
        var layerItem = {
            props: ['order'],
              components: {
                'single-layer':singleLayer             
            },
            data:function(){
            	return{
                    watchLayerChange:false,   //图层变化监控，如图层变动，该值反向
                    watchAddLayer:false
            	}
            },
            template: `
            <div id="test">
                    <div v-show="order === 1">
                        <button id="drawDiscrictButton" type="button" class="am-btn am-btn-secondary am-radius am-btn-block" @click=addLayer>新建图层</button>
                        <button type="button" class="am-btn am-btn-success am-radius am-btn-block">导入数据</button>
                        <single-layer :layerChange="watchLayerChange" :newLayer=watchAddLayer @layerwatch=layerChange @drawlayerfromson=startDraw ></single-layer>
                    </div>
                    <div v-show="order === 2">
                        {{order}}
                    </div>
                    <div v-show="order === 3">
                        {{order}}
                    </div>
                    
                    <div v-show ="order === 4">
                    {{order}}
                    </div>
                
            </div>
          ` ,
          methods:{
            addLayer(){
                    var that = this;
                    layui.use(['layer', 'form'], function(){
                    var layer = layui.layer
                    ,form = layui.form;
					  layer.prompt({title: '图层名称', formType: 2}, function(text, index){
					    layer.close(index);
					  
					    var postconfig={
                                method: 'post',
                                url:'addlayer',
                                data:{
                                    layerName:text,
                   					
                                   
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
                               that.watchLayerChange =!that.watchLayerChange;
                                console.log(response);
                                 that.startDraw();
                                
                            })
                            .catch(function (error) {
                                console.log(error);
                            
                            });  //axios
					    
					  });//promt
						
				});
                 
                   
            },
            layerChange(){
            	this.watchLayerChange = !this.watchLayerChange;
            },
            startDraw(){
                this.watchAddLayer=!this.watchAddLayer;
                
            }


          }
        } ;