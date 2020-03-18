<template>
  <div class="modal fade" id="createPreprocessorNormalizerModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="createPreprocessorNormalizerModalTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="createPreprocessorNormalizerModalTitle">Use Preprocessor / Normalizer</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">

          <div class="form-group">

            <fieldset>
              <label class="control-label" for="layerTypeField">Available Preprocessor / Normalizer</label>
              <select
                class="custom-select"
                id="layerTypeField"
                v-model="newModel.type"
              >
                <option v-for="item in helper.preprocessorNormalizerTypes" :value="item.key">{{item.description}}</option>
              </select>
            </fieldset>

          </div>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button
            :disabled="newModel.type==='' || newModel.name===''"
            type="button"
            class="btn btn-primary"
            v-on:click="createNNLayer">Select</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';

  export default {
    data() {
      return {
        newModel: {
          type: '',
        },
        error: false,
      }
    },
    computed: {
      ...mapGetters('dl4j',
          {
            helper: 'getHelperData',
          },
      ),
    },
    methods: {
      openModal() {
        this.newModel.type = '';
        $('#createPreprocessorNormalizerModal').modal('show')
      },
      createNNLayer() {
        this.$emit('newItem', this.newModel);
        $('#createPreprocessorNormalizerModal').modal('hide')
      },
    }
  }
</script>
