angular.module('myApp', ['schemaForm'])
       .controller('FormController', function($scope) {
  $scope.schema = {
                    "type": "object",
                    "properties": {
                      "select": {
                        "title": "Select without titleMap",
                        "type": "string",
                        "enum": [
                          "a",
                          "b",
                          "c"
                        ]
                      },
                      "select2": {
                        "title": "Select with titleMap (old style)",
                        "type": "string",
                        "enum": [
                          "a",
                          "b",
                          "c"
                        ]
                      },
                      "noenum": {
                        "type": "string",
                        "title": "No enum, but forms says it's a select"
                      },
                      "array": {
                        "title": "Array with enum defaults to 'checkboxes'",
                        "type": "array",
                        "items": {
                          "type": "string",
                          "enum": [
                            "a",
                            "b",
                            "c"
                          ]
                        }
                      },
                      "array2": {
                        "title": "Array with titleMap",
                        "type": "array",
                        "default": [
                          "b",
                          "c"
                        ],
                        "items": {
                          "type": "string",
                          "enum": [
                            "a",
                            "b",
                            "c"
                          ]
                        }
                      },
                      "radios": {
                        "title": "Basic radio button example",
                        "type": "string",
                        "enum": [
                          "a",
                          "b",
                          "c"
                        ]
                      },
                      "radiobuttons": {
                        "title": "Radio buttons used to switch a boolean",
                        "type": "boolean",
                        "default": false
                      },
                        "comments": {
                              "type": "array",
                              "maxItems": 2,
                              "items": {
                                "type": "object",
                                "properties": {
                                  "name": {
                                    "title": "Name",
                                    "type": "string"
                                  },
                                  "email": {
                                    "title": "Email",
                                    "type": "string",
                                    "pattern": "^\\S+@\\S+$",
                                    "description": "Email will be used for evil."
                                  },
                                  "spam": {
                                    "title": "Spam",
                                    "type": "boolean",
                                    "default": true
                                  },
                                  "comment": {
                                    "title": "Comment",
                                    "type": "string",
                                    "maxLength": 20,
                                    "validationMessage": "Don't be greedy!"
                                  }
                                },
                                "required": [
                                  "name",
                                  "comment"
                                ]
                              }
                    }
                  }
                  };

  $scope.form = [
                  "select",
                  {
                    "key": "select2",
                    "type": "select",
                    "titleMap": {
                      "a": "A",
                      "b": "B",
                      "c": "C"
                    }
                  },
                  {
                    "key": "noenum",
                    "type": "select",
                    "titleMap": [
                      {
                        "value": "a",
                        "name": "A"
                      },
                      {
                        "value": "b",
                        "name": "B"
                      },
                      {
                        "value": "c",
                        "name": "C"
                      }
                    ]
                  },
                  "array",
                  {
                    "key": "array2",
                    "type": "checkboxes",
                    "titleMap": [
                      {
                        "value": "a",
                        "name": "A"
                      },
                      {
                        "value": "b",
                        "name": "B"
                      },
                      {
                        "value": "c",
                        "name": "C"
                      }
                    ]
                  },
                  {
                    "key": "radios",
                    "type": "radios",
                    "titleMap": [
                      {
                        "value": "c",
                        "name": "C"
                      },
                      {
                        "value": "b",
                        "name": "B"
                      },
                      {
                        "value": "a",
                        "name": "A"
                      }
                    ]
                  },
                  {
                    "key": "radiobuttons",
                    "type": "radiobuttons",
                    "titleMap": [
                      {
                        "value": false,
                        "name": "No way"
                      },
                      {
                        "value": true,
                        "name": "OK"
                      }
                    ]
                  },
                  {
                      "key": "comments",
                      "add": "New",
                      "style": {
                        "add": "btn-success"
                      },
                      "items": [
                        "comments[].name",
                        "comments[].email",
                        {
                          "key": "comments[].spam",
                          "type": "checkbox",
                          "title": "Yes I want spam.",
                          "condition": "model.comments[arrayIndex].email"
                        },
                        {
                          "key": "comments[].comment",
                          "type": "textarea"
                        }
                      ]
                    }
                ];

  $scope.model = {};
});