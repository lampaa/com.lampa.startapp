#import <Cordova/CDV.h>

@interface startApp : CDVPlugin

- (void)check:(CDVInvokedUrlCommand*)command;
- (void)start:(CDVInvokedUrlCommand*)command;

@end