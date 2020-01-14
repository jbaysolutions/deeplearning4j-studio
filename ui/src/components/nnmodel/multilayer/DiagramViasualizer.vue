<template>
  <div class="card mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened">
      <i class="fas fa-project-diagram"></i>
      Diagram
      <button
        type="button"
        class="close"
      >
        <i v-if="opened" class="fas fa-minus-square"></i>
        <i v-else class="fas fa-plus-square"></i>
      </button>
    </div>
    <div class="collapse show" :id="widgetId">
      <div class="card-body" id="mynetwork" style="height: 400px">
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';

  export default {
    components: {
    },
    props: {
      model: {
        type: Object,
        required: true,
      },
    },
    data() {
      return {
        widgetId: this.generateID(),
        opened: true,
      }
    },
    watch: {
      model() {
        this.generateNodes();
      }
    },
    computed: {
      rawData() {

        let temp = {
          layers: []
        };

        if (!this.model || !this.model.layers)
          return temp;

        let id = 0 ;
        let i = 0 ;
        for ( i = 0 ; i < this.model.layers.length; i++) {
          if ( i === 0) {
            temp.layers.push({
              id: id++,
              size: parseInt(this.model.inputNodes),
              type: 'DENSE',
            });
          }
          temp.layers.push({
            id: id++,
            size: parseInt(this.model.layers[i].numNodes) ,
            type: this.model.layers[i].type + '',
          });
        }
        temp.layers.push({
          id: id++,
          size: parseInt(this.model.outputLayer.numNodes) ,
          type: this.model.outputLayer.type + '',
        });

        return temp;
      }
    },
    methods: {
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
      generateNodes() {

        let self = this;
        var nodes = new vis.DataSet();
        var edges = new vis.DataSet();
        var layersDecomposed = [];

        var layers = this.rawData.layers;
        var layerid = 0 ;
        var nodeid = layers.length ;
        var horizontalLocation = 0 ;

        var biggestLayer = 0 ;
        layers.forEach(layer => {
            if (layer.size > biggestLayer) {
                biggestLayer = layer.size;
            }
        });
        if ( biggestLayer > 10) biggestLayer = 10;

        layers.forEach(layer => {
            let specificLayer = [];

            var temp = layer.size;
            if ( temp > 10) temp = 10;
            var diff = (biggestLayer - temp)/2*60;

            /*nodes.add([{
                id: layerid++,
                //label: "Settings",
                shadow: false,
                shape: "icon",
                icon: {
                    face: '"Font Awesome 5 Free"',
                    code: "\uf085",
                    size: 25,
                    color: "#1a1a1a"
                },
                //height: 250,
                chosen: {label: false},
                x: horizontalLocation + 25 ,
                y: -25 + diff,
            }]);*/

            var i = 1;
            for (i = 0; i < layer.size && i < 10; i++) {
                var nnn = {
                    id: nodeid++,
                    // label: layer.type + '' + (nodeid - 1),
                    x: horizontalLocation,
                    y: (i * 60) + diff,
                    group: horizontalLocation,
                    shadow: true,
                    shape: "dot",
                    size: 15,
                };
                nodes.add(
                    [
                        nnn
                    ]
                );
                specificLayer.push(nnn.id)
            }
            ;
            layersDecomposed.push(specificLayer);
            horizontalLocation += 240;
        });

        var l = 1;
        //console.log("Layer size 1 : " + layersDecomposed.length) ;
        for (l = 0; l < layersDecomposed.length-1; l++) {
            let specificLayer = layersDecomposed[l];
            var n = 1;
            for (n = 0; n < specificLayer.length; n++) {
                let specificNode = specificLayer[n];
                let destLayer = layersDecomposed[l+1];
                // Destination Layer
                var d = 1;
                for (d = 0; d < destLayer.length; d++) {
                    //console.log("Adding " + specificNode + " to " + destLayer[d] );
                    edges.add(
                        [
                            {from: specificNode, to: destLayer[d]}
                        ]
                    );
                }
            }
        }

        // create a network
        var container = document.getElementById('mynetwork');
        var data = {
          nodes: nodes,
          edges: edges,
          options: {
            physics: false,
            interaction: {
              dragNodes: false,
                dragView: false,
                zoomView: false,
                selectable: false,
            }
          }
        };
        var options = {};
        var network = new vis.Network(container, data, options);

        network.on("beforeDrawing", function (ctx)
        {
            ctx.lineWidth = 1;
            ctx.shadowBlur = 10;

            layersDecomposed.forEach((layerUnit, index) => {
                ctx.beginPath();

                if ( index === 0) {

                  ctx.fillStyle = 'lightgreen';
                  ctx.shadowColor = 'green';
                  ctx.strokeStyle = 'green';
                } else if ( index === layers.length-1) {
                  ctx.fillStyle = 'red';
                  ctx.shadowColor = 'red';
                  ctx.strokeStyle = 'red';
                } else {
                  ctx.fillStyle = 'orange';
                  ctx.shadowColor = 'orange';
                  ctx.strokeStyle = 'orange';
                }

                var pos = network.getPositions(layerUnit);
                ctx.moveTo(pos[layerUnit[0]].x-40, pos[layerUnit[0]].y-40);
                ctx.lineTo(pos[layerUnit[layerUnit.length-1]].x-40, pos[layerUnit[layerUnit.length-1]].y+45);
                ctx.lineTo(pos[layerUnit[layerUnit.length-1]].x+40, pos[layerUnit[layerUnit.length-1]].y+45);
                ctx.lineTo(pos[layerUnit[layerUnit.length-1]].x+40, pos[layerUnit[0]].y-40);
                ctx.lineTo(pos[layerUnit[0]].x-40, pos[layerUnit[0]].y-40);

                ctx.fill();
                ctx.stroke();

            });


            ctx.fillStyle='#fbfbfb';
            ctx.shadowBlur = 0;
            ctx.strokeStyle = '#fbfbfb';


            layersDecomposed.forEach((layerUnit, index) => {
                var pos = network.getPositions(layerUnit);
                ctx.fillText("Type: " + layers[index].type, pos[layerUnit[0]].x-40, 0-72);
                ctx.fillText("Nodes: " + layers[index].size, pos[layerUnit[0]].x-40, 0-58);
            });
        });
        /*network.on("afterDrawing", function (ctx)
        {

        });*/
        /*network.on("click", function (params) {
            if (this.getNodeAt(params.pointer.DOM) < layers.length) {
              //self.selectedNode = this.getNodeAt(params.pointer.DOM);
              this.redraw();
              this.moveTo(
                {
                  position: {
                    x: params.pointer.canvas.x ,
                    y: params.pointer.canvas.y
                  },
                  scale: 1,
                  animation: true,
                }
              );
              console.log(
                  "LOG2 : " + JSON.stringify(params, null, 4)
              );
            }
        });*/
      },
    }
  }
</script>

