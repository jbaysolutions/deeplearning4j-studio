<template>
  <div class="App">
    <!-- TOP MENU BAR -->
    <TopMenuBar/>
    <div class="container-fluid">
      <div class="row">

        <!-- LEFT MENU BAR -->
        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
          <LeftMenuBar/>
        </nav>

        <!-- MAIN CONTENT CONTAINER -->
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
          <router-view/>
        </main>
      </div>
    </div>
    <notifications/>
  </div>
</template>
<style>
  .sidebar {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    z-index: 100; /* Behind the navbar */
    padding: 48px 0 0; /* Height of navbar */
    box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
  }

  .sidebar-sticky {
    position: relative;
    top: 0;
    height: calc(100vh - 48px);
    padding-top: .5rem;
    overflow-x: hidden;
    overflow-y: auto; /* Scrollable contents if viewport is shorter than content. */
  }

  /*
   * Content
   */

  [role="main"] {
    padding-top: 133px; /* Space for fixed navbar */
  }

  @media (min-width: 768px) {
    [role="main"] {
      padding-top: 48px; /* Space for fixed navbar */
    }
  }
</style>
<script>
  import TopMenuBar from 'components/menus/TopMenuBar'
  import LeftMenuBar from 'components/menus/LeftMenuBar'
  import {mapActions, mapGetters} from 'vuex';


  export default {
    mounted () {
      this.loadHelperData();
    },
    data () {
      return {
        appSummary: ''
      }
    },
    methods: {
      ...mapActions('dl4j',
          {
              loadHelperData: 'loadHelperData',
          },
      ),
      /*getSummary (cb) {
        return fetch(`/v1/summary`, {
          accept: "application/json"
        })
          .then(this.checkStatus)
          .then(this.parseJSON)
          .then(cb)
      },

      checkStatus (response) {
        if (response.status >= 200 && response.status < 300) {
          return response;
        }
        const error = new Error(`HTTP Error ${response.statusText}`);
        error.status = response.statusText;
        error.response = response;
        console.log(error); // eslint-disable-line no-console
        throw error;
      },

      parseJSON (response) {
        return response.json();
      }*/
    },
    components: {
      TopMenuBar,
      LeftMenuBar,
    }
  }
</script>
