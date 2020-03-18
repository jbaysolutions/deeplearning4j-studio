<template>
  <div class="card mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened" style="cursor: pointer">
      <i class="fas fa-random"></i>
      Preprocessor / Normalizer
      <span  v-if="!training.rawStrategy.preprocessorNormalizerWrapper || training.rawStrategy.preprocessorNormalizerWrapper===null" class="badge badge-pill badge-danger">Nothing set</span>
      <span  v-else class="badge badge-pill badge-success">Preprocessor set</span>
      <button
              type="button"
              class="close"
      >
        <i v-if="opened" class="fas fa-minus-square"></i>
        <i v-else class="fas fa-plus-square"></i>
      </button>
    </div>
    <div class="collapse" :id="widgetId" >
      <div class="card-body">
        <div class="row mb-3" v-if="showNormalizer">
          <div class="col-12">
            <NormalizerStandardizeConfig
              v-if="training.rawStrategy.preprocessorNormalizerWrapper && training.rawStrategy.preprocessorNormalizerWrapper.type==='NORMALIZER_STANDARDIZE'"
              @changed="notifyChange()"
              @toDelete="deletePreprocessorNormalizer"
            />
          </div>
        </div>
        <div class="row mb-3" v-else>
          <div class="col-12 text-center">
            <button type="button" class="btn btn-primary" v-on:click="$refs.createPreprocessorNormalizerModal.openModal()">
              <i class="fas fa-plus"></i>
              Select Preprocessor / Normalizer
            </button>
          </div>
        </div>
      </div>
    </div>
    <CreatePreprocessorNormalizerModal
      ref="createPreprocessorNormalizerModal"
      @newItem="createPreprocessorNormalizer"
    />
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';

  import CreatePreprocessorNormalizerModal from '../../../modals/CreatePreprocessorNormalizerModal'
  import NormalizerStandardizeConfig from './util/NormalizerStandardizeConfig'


  export default {
    components: {
      CreatePreprocessorNormalizerModal,
      NormalizerStandardizeConfig
    },
    mounted() {
    },
    data() {
      return {
        opened: false,
        widgetId: this.generateID(),
      }
    },
    computed: {
      ...mapGetters('dl4j',
        {
          training: 'getCurrentTrainingStrategy',
        },
      ),
      showNormalizer() {
        console.log(" -----> " + this.training.rawStrategy.preprocessorNormalizerWrapper);
        if (!this.training.rawStrategy.preprocessorNormalizerWrapper)
          return false;
        return this.training.rawStrategy.preprocessorNormalizerWrapper !== null;
      }
    },
    methods: {
      ...mapActions('dl4j',
        {
          generateCleanPreprocessorNormalizer: 'generateCleanPreprocessorNormalizer',
        },
      ),
      notifyChange() {
        this.$emit('changed');
      },
      createPreprocessorNormalizer(request) {
        this.generateCleanPreprocessorNormalizer(request.type)
          .then((response) => {
            this.training.rawStrategy.preprocessorNormalizerWrapper = response;
            this.notifyChange();
          }).catch((error) => {
            console.log('Error detected ? ' + error);
            throw error
          });
      },
      deletePreprocessorNormalizer() {
        this.training.rawStrategy.preprocessorNormalizerWrapper = null;
        this.notifyChange();
      },
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
    }
  }
</script>
