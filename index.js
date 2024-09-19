/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import SimpleReactScreen from './SimpleReactScreen';

AppRegistry.registerComponent(appName, () => App);
AppRegistry.registerComponent('SimpleReactScreen', () => SimpleReactScreen);
