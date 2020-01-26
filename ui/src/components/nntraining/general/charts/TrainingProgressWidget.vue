<script>
  import { Line, mixins } from 'vue-chartjs';
  const { reactiveProp } = mixins;

  export default {
    extends: Line,
    mixins: [reactiveProp],
    data() {
      return {
        options: {
          responsive: true,
          maintainAspectRatio: false,
          legend: {
            labels: {
              usePointStyle: false,
              fontColor: 'white'
            }
          },
          scales: {
            xAxes: [{
              display: true,
              color: '#EEEEEE',
              scaleLabel: {
                display: true,
                labelString: 'Epoch'
              }
            }],
            yAxes: [{
              display: true,
              color: '#EEEEEE',
              scaleLabel: {
                display: true,
                labelString: 'Score'
              }
            }]
          },
          title: {
            display: true,
            text: 'Training'
          }
        }
      }
    },
    watch: {
      chartData () {
        //this.$data._chart.update();
        this.renderChart();
      }
    },
    mounted () {
      Chart.defaults.global.defaultFontColor = 'white';
      this.renderChart(this.chartData, this.options)
    },
    methods:{
      refreshChart() {
        //this.renderChart(this.chartData, this.options);
        this.$data._chart.update();
      }
    }
  }
</script>
