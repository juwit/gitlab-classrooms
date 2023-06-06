const path = require('path')

module.exports = [
  {
    entry: {
      model: './src/model/model.module.ts',
      view: './src/view/view.module.ts',
    },
    module: {
      rules: [
        {
          test: /\.ts$/i,
          use: 'ts-loader'
        }
      ],
    },
    mode: 'production',
    resolve: {
      extensions: ['.ts', '.js'],
    },
    output: {
      filename: 'quiz-[name]-esm.js',
      path: path.resolve(__dirname, 'dist'),
      library: {
        type: 'module'
      }
    },
    experiments: {
      outputModule: true,
    },
  },
  {
    entry: {
      model: './src/model/model.module.ts',
      view: './src/view/view.module.ts',
    },
    module: {
      rules: [
        {
          test: /\.ts$/i,
          use: 'ts-loader'
        }
      ],
    },
    mode: 'production',
    resolve: {
      extensions: ['.ts', '.js'],
    },
    output: {
      filename: 'quiz-[name]-esm.js',
      path: path.resolve(__dirname, '../resources/public/scripts'),
      library: {
        type: 'module'
      }
    },
    experiments: {
      outputModule: true,
    },
  }
  ]
