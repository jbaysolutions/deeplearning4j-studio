<template>
  <div class="row mt-3">
    <div class="col-12">
      <div class="row">
        <div class="col">
        </div>
        <div class="col">
          <span v-if="updated" class="badge badge-danger">Model was modified, requires saving</span>
        </div>
        <div class="col">
          <div v-if="updated" class="text-right">
            <button class="btn btn-primary" v-on:click="saveModel">
              Save
            </button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="training.rawStrategy.trainingType==='LOCAL'" class="col-12">
      LOCAL
    </div>

    <div v-if="training.rawStrategy.trainingType==='SPARK'" class="col-12">
      SPARK
    </div>

  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';

  export default {
    components: {},
    mounted() {
    },
    computed: {
      ...mapGetters('dl4j',
        {
          modeldata: 'getCurrentModel',
          training: 'getCurrentTrainingStrategy',
        },
      ),
    },
    data() {
      return {
        updated: false,
      }
    },
  }
</script>
