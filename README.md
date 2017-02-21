# libajp13 - A complete AJPv1.3 Java library

**libajp13** is a fully featured open source library implementing the Apache JServ Protocol version 1.3 (ajp13), based on the [Apache Protocol Reference](https://tomcat.apache.org/connectors-doc/ajp/ajpv13a.html).

The library has been developed from Espen Wiborg's [ajp_client](https://github.com/espenhw/ajp-client), licensed under the Apache License 2.0. At this point, most of the code has been refactored and improved to support *all* AJP13 packet types.  

![AJP13 in Wireshark](http://i.imgur.com/e9wTKDS.png "AJP13 in Wireshark")

As of 02/27/2017, the JaCoCoverage analysis of project "libajp13" reports: 
![Test Code Coverage](http://i.imgur.com/ADD_HERE_TCOVERAGE.png"Test Code Coverage")

### Issues
This implementation is derived from Dan Milstein's reversing work, based on Tomcat 3.x AJP code. If you've discovered a bug, please open an issue in Github.

### How To Use it
The following code examples show how to use *libajp13*. 

For more details, please refer to the official [JavaDoc](http://doyensec.github.io/libajp13/).

_CPing and CPong_
```java
//Create a CPing message
AjpMessage msg = new CPingMessage();
//Send the content of the packet - msg.getBytes()
[...]
AjpMessage reply = AjpReader.parseMessage(gotBytes);
if (reply instanceof CPongMessage) {
  System.out.println("[OK] Valid CPong");
}
```
_Shutdown_
```java
AjpMessage msg = new ShutdownMessage();
```

_EndResponse with session reuse set to 'true'_
```java
AjpMessage msg = new EndResponseMessage(true);
```

_SendBodyChunkMessage_
```java
AjpMessage msg = new SendBodyChunkMessage("ABCD".getBytes());
```

_SendHeadersMessage_
```java
List<Pair<String, String>> headers = new LinkedList<>();
headers.add(Pair.make("Content-Type","text/html; charset=utf-8"));
AjpMessage msg = new SendHeadersMessage(200,"OK",headers);
```

_GetBodyChunkMessage_
```java
AjpMessage msg = new GetBodyChunkMessage(10);
```

_BodyMessage_
```java
AjpMessage msg = new BodyMessage("MyStringSentAsBytes".getBytes());
```

_ForwardRequestMessage to build a simple GET request_
```java
List<Pair<String, String>> headers = new LinkedList<>();
headers.add(Pair.make("Content-Type","text/html; charset=utf-8"));
AjpMessage msg = new ForwardRequestMessage(2, new URL("http://127.0.0.1/"), headers, null);
```

_ForwardRequestMessage using ForwardRequestMessageGetBuilder_
```java
AjpMessage msg = ForwardRequestMessage.ForwardRequestMessageGetBuilder(new URL("http://192.168.1.1/log/"));
```

_ForwardRequestMessage to build a PUT request with custom headers and attributes_
```java
List<Pair<String, String>> headers = new LinkedList<>();
headers.add(Pair.make("Content-Type","text/html; charset=utf-8"));
headers.add(Pair.make("CustomHeaderName","CustomHeaderValue"));
List<Pair<String, String>> attributes = new LinkedList<>();
attributes.add(Pair.make("jvm_route","3131212"));
attributes.add(Pair.make("custom_attribute","custom_value"));
AjpMessage msg = new ForwardRequestMessage(5, "HTTP/1.0", "/api/", "127.0.0.1", "localhost", "127.0.0.1", 8009, true, headers, attributes);
``` 

### Useful links
* https://tomcat.apache.org/connectors-doc/ajp/ajpv13a.html
* https://tomcat.apache.org/tomcat-7.0-doc/api/org/apache/coyote/ajp/Constants.html
* https://github.com/kohsuke/ajp-client
* http://isu.ifmo.ru/docs/IAS904/web.904/q20202/protocol/AJPv21.html
* http://en.wikipedia.org/wiki/Apache_JServ_Protocol
* https://tomcat.apache.org/tomcat-7.0-doc/config/ajp.html