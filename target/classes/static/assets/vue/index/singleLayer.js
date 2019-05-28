//单个图层组件,父组件为layerItem
        var singleLayer = {
  			props:['layerChange'],
  			data:function(){
            	return {
            		layersget:[{layerId:null,layerName:null,layerData:null,userId:null}],
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
                      }
                      
                  
            	}
            },
  			template:`
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
                                
                        	<button type="button" class="am-close"  :style="closeButton" @click=deleteLayer($event,layer.layerId)>&times;</button>
                                 
                              
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
				
				deleteLayer(e,layerId){   //删除图层
					var that = this;
					
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
                                    id:layerId,
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
                                
                              	that.deleteSuccess();
                                console.log(response);
                                
                                
                            })
                            .catch(function (error) {
                                console.log(error);
                            
                            });  //axios
							
						}); //layerconfirm
					   
					   
						
					});//layeruse
					
				},
				deleteSuccess(){   //与父组件通信，告诉父组件图层变化。
				
					this.$emit('layerwatch');   //图层发生变化，
					
                },
              
				drawLayer(){
					this.$emit('drawlayerfromson');
				}
			}
							
          }   ;
          
