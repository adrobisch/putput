var _ = require('lodash');

module.exports = function (grunt) {

  // any of [ 'PhantomJS', 'Chrome', 'Firefox', 'IE']
  var TEST_BROWSERS = ((process.env.TEST_BROWSERS || '').replace(/^\s+|\s+$/, '') || 'PhantomJS').split(/\s*,\s*/g);

  require('time-grunt')(grunt);
  require('load-grunt-tasks')(grunt);

  var mainFile = "app.js";
  var webPackConfig = require("./webPackConfig.js")("./src/js/" + mainFile, "<%= config.dist %>", mainFile);

  grunt.initConfig({

    pkg: grunt.file.readJSON('package.json'),

    config: {
      sources: 'src/js',
      css: 'src/css',
      dist: '../target/classes/web',
      assets: 'assets',
      tests: 'test',
      node_modules: 'node_modules'
    },

    jshint: {
      src: [ '<%= config.sources %>' ],
      gruntfile: [ 'Gruntfile.js' ],
      options: {
        jshintrc: true
      }
    },

    less: {
      dist: {
        options: {
          paths: [
            "<%= config.css %>",
            "<%= config.node_modules %>/bootstrap/less"
          ],
          cleancss: true
        },
        files: {
          "<%= config.dist %>/css/style.css": "src/css/style.less"
        }
      }
    },

    karma: {
      options: {
        configFile: '<%= config.tests %>/config/karma.unit.js'
      },
      single: {
        singleRun: true,
        autoWatch: false,

        browsers: TEST_BROWSERS,

        browserify: {
          debug: false,
          transform: [ 'brfs' ]
        }
      },
      unit: {
        browsers: TEST_BROWSERS
      }
    },
    uglify: {
      dist: {
        options: {
          sourceMap: true,
          sourceMapIncludeSources: true
        },
        files: {
          '<%= config.dist %>/app.js': [ '<%= config.dist %>/app.js' ]
        }
      }
    },
    copy: {
      resources: {
        files: [
          {
            expand: true,
            cwd: '<%= config.sources %>',
            src: [ '*.html' ],
            dest: '<%= config.dist %>'
          },

          // assets
          {
            expand: true,
            cwd: '<%= config.assets %>',
            src: [
              '**/*'
            ],
            dest: '<%= config.dist %>'
          },
          // dist folders
          {
            expand: true,
            cwd: '<%= config.node_modules %>',
            src: [
              '**/*.min.js',
              '**/*.min.css',
              'fonts/**',
              'js/**/*.min.js',
              '**/dist/**/*'
            ],
            dest: '<%= config.dist %>/vendor'
          }

        ]
      }
    },

    webpack: {
      build: webPackConfig,
      watch: _.merge({
        failOnError: false // don't report error to grunt if webpack find errors
        // Use this if webpack errors are tolerable and grunt should continue
      }, webPackConfig)
    },

    watch: {
      resources: {
        files: [
          '<%= config.assets %>/img/**/*',
          '<%= config.assets %>/css/**/*',
          '<%= config.sources %>/*.html'
        ],
        tasks: [ 'copy:resources' ]
      },
      styles : {
        files: [
          '<%= config.css %>/*.less'
        ],
        tasks: [ 'less' ]
      }
    }
  });

  grunt.registerTask('test', 'jshint', 'karma:single');
  grunt.registerTask('build', [ 'less', 'webpack:build', 'copy' ]);
  grunt.registerTask('dist', [ 'build', 'uglify' ]);
  grunt.registerTask('rebuild', [
    'build',
    'watch'
  ]);

  grunt.registerTask('default', ['build']);
};
