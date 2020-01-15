<template>
  <div>
    <div class="row">
      <div class="col-4">
        <fieldset>
          <div class="form-group">
            <label for="exampleCellDelimiter">Cell Delimiter</label>
            <input v-model="reader.delimiter"
                   type="text"
                   maxlength="1"
                   class="form-control" id="exampleCellDelimiter"
                   aria-describedby="exampleCellDelimiterHelp"
                   placeholder="Cell delimiter char">
            <small id="exampleCellDelimiterHelp" class="form-text text-muted">
              Character used as a Cell Delimiter. Only one Char!
            </small>
          </div>
        </fieldset>
      </div>
      <div class="col-4">
        <fieldset>
          <div class="form-group">
            <label for="exampleQuoteDelimiter">Quote Delimiter</label>
            <input v-model="reader.quote"
                   type="text"
                   maxlength="1"
                   class="form-control" id="exampleQuoteDelimiter"
                   aria-describedby="exampleQuoteDelimiterHelp"
                   placeholder="Quote delimiter char">
            <small id="exampleQuoteDelimiterHelp" class="form-text text-muted">Character used as a Text Delimiter. Only
              one Char!
            </small>
          </div>
        </fieldset>
      </div>
      <div class="col-4">
        <fieldset>
          <div class="form-group">
            <label for="inputSkipLines">Skip Lines</label>
            <input v-model="reader.skipLines"
                   type="number"
                   maxlength="1"
                   class="form-control" id="inputSkipLines"
                   aria-describedby="inputSkipLinesHelp"
                   placeholder="0">
            <small id="inputSkipLinesHelp" class="form-text text-muted">Number of lines to skip from the CSV (the header, for example)
            </small>
          </div>
        </fieldset>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';

  export default {
    components: {
    },
    mounted() {
    },
    data() {
      return {
      }
    },
    computed: {
      ...mapGetters('dl4j',
        {
          training: 'getCurrentTrainingStrategy',
        },
      ),
      reader() {
        if (this.training && this.training.rawStrategy && this.training.rawStrategy.recordReader) {
          return this.training.rawStrategy.recordReader;
        } else {
          return {
            delimiter: 'ERROR',
            quote: 'ERROR',
            skipLines: -1,
          };
        }
      }
    },
    methods: {
      generateID() {
        return (Math.random() + "").replace("0.", "AAA");
      },
    }
  }
</script>
