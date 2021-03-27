module.exports = {
  "presets": [
    [
      "@babel/preset-env",
      {
        "targets": {
          "ie": "11"
        },
        "useBuiltIns": "entry",
        "corejs": 3,
        "debug": true
      }
    ]
  ],
  "plugins":[
    "@babel/plugin-proposal-class-properties",
    "@babel/plugin-transform-template-literals"
  ]
};
