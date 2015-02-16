var _ = require('lodash');

module.exports = function (grunt) {

  function browserify(extraOptions) {
    var options = {
      browserifyOptions: {
        builtins: [ 'fs' ],
        commondir: false,
        detectGlobals: false,
        insertGlobalVars: []
      },
      transform: [ 'brfs' ]
    };

    var config = {
      options: _.merge(options, extraOptions),
      files: {
        '<%= config.dist %>/app.js': [ '<%= config.sources %>/app.js' ]
      }
    };

    return config;
  }

  // any of [ 'PhantomJS', 'Chrome', 'Firefox', 'IE']
  var TEST_BROWSERS = ((process.env.TEST_BROWSERS || '').replace(/^\s+|\s+$/, '') || 'PhantomJS').split(/\s*,\s*/g);

  require('time-grunt')(grunt);
  require('load-grunt-tasks')(grunt);

  grunt.loadNpmTasks('grunt-contrib-less');

  grunt.initConfig({

    pkg: grunt.file.readJSON('package.json'),

    config: {
      sources: 'src/js',
      css: 'src/css',
      dist: '../target/classes/web',
      assets: 'assets',
      tests: 'test',
      bower_components: 'bower_components'
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
            "<%= config.bower_components %>/bootstrap/less"
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
    browserify: {
      app: browserify({}),
      watch: browserify({ watch: true })
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
          // bower dist folders
          {
            expand: true,
            cwd: '<%= config.bower_components %>',
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

  grunt.registerTask('test', 'karma:single');
  grunt.registerTask('build', [ 'less', 'browserify:app', 'copy' ]);
  grunt.registerTask('dist', [ 'build', 'uglify' ]);
  grunt.registerTask('rebuild', [
    'build',
    'browserify:watch',
    'watch'
  ]);

  grunt.registerTask('default', [ 'jshint', 'test', 'build', 'uglify' ]);
};
