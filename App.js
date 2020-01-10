/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {View,Text,ScrollView,Button,NativeModules} from 'react-native';
export const CallbackModule = NativeModules.CallbackModule1;
export default class App extends Component<{}> {

    state = {
        text:''
    }
  render() {
    return (
        <ScrollView>
            <Button
                title={'扫码'}
                onPress={() => {
                    CallbackModule.doScan().then(re => {
                        this.setState({
                            text:re
                        })
                    });
                }}>
            </Button>
            <View>
                <Text>扫描到的文本为:{this.state.text}</Text>
            </View>
        </ScrollView>
    );
  }
}
