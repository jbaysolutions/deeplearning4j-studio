<template>
  <div class="row">
    <div class="col-6">
      <div class="form-group">
        <label for="inputRootDirFileSplit">Root Dir or Specific File</label>
        <input v-model="inputSplit.rootDir"
               v-on:change="notifyChange()"
               type="text"
               class="form-control" id="inputRootDirFileSplit"
               aria-describedby="inputRootDirFileSplitHelp"
               placeholder="">
        <small id="inputRootDirFileSplitHelp" class="form-text text-muted">
          Root Dir to read files from, of specific File on the filesystem
        </small>
      </div>
    </div>
    <div class="col-6"></div>
    <div class="col-6">
      <div class="form-group">
        <div class="custom-control custom-switch">
          <input type="checkbox" class="custom-control-input" :id="'splitFileRecursiveField'+splitterid" v-model="inputSplit.recursive"  v-on:change="notifyChange()">
          <label class="custom-control-label" :for="'splitFileRecursiveField'+splitterid">Recursive?</label>
        </div>
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
    props: {
      position: {
        type: Number,
        required: true,
      },
    },
    data() {
      return {
        splitterid: this.generateID(),
      }
    },
    computed: {
      ...mapGetters('dl4j',
        {
          training: 'getCurrentTrainingStrategy',
        },
      ),
      inputSplit() {
        if (this.training && this.training.rawStrategy && this.training.rawStrategy.recordReader[this.position] && this.training.rawStrategy.recordReader[this.position].inputSplit) {
          return this.training.rawStrategy.recordReader[this.position].inputSplit;
        } else {
          return {
            allowFormat: [],
            recursive: true,
            rootDir: "",
            type: "FILE_SPLIT",
          };
        }
      }
    },
    methods: {
      notifyChange() {
        this.$emit('changed');
      },
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
    }
  }
</script>
