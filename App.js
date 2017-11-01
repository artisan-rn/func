/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import RootTabNavigator from './src/RootTabNavigator'
import RootStackNavigator from './src/RootStackNavigator'
import RootDrawer from './src/RootDrawer'

export default class App extends Component<{}> {
  render() {
    return (
      <RootDrawer/>
    );
  }
}