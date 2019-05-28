//菜单组件,父组件为index页面,在页面左侧
        var menuItem = {
        props: ['labeltext','styleobject','order'],
		template: `
          <li class="sidebar-nav-link" @click=menuclick>
                    <a href="#">
                        <i v-bind:class="styleobject"></i> {{ labeltext }}
                    </a>
         </li>
          `  ,
          methods:{
                menuclick(){
                    
                    this.$emit('menuitemclick',this.order);
                }
          }   
        };

        