'use strict';


describe('environment', function() {


  it('should provide angular', function() {

    // when
    var angular = require('angular');

    // then
    expect(angular.module).toBeDefined();
  });


  it('should use jQuery within angular', function() {

    var angular = require('angular');

    // when
    var body = angular.element('body');

    // then
    expect(body.click).toBeDefined();
  });


  it('should provide jQuery', function() {

    var jQuery = require('jquery');

    // when
    var body = jQuery('body');

    // then
    expect(body.on).toBeDefined();
  });
});