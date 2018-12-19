# Jsonsmith

A java library for roughly creating JSON-like objects.

# Usage

## ObjectBuilder

For example,

``` java
Object obj =
    new ObjectBuilder()
        .put(".hello", "jsonsmith")
        .put(".items[0].key", 1)
        .put(".items[0].value", "item-0-value")
        .put(".items[2].key", 2)
        .put(".items[2].value", "item-1-value")
        .put(".set.very.deep.property.directly", true)
        .build();

// jackson
String json = new ObjectMapper()
    .enable(SerializationFeature.INDENT_OUTPUT)
    .writeValueAsString(obj)

System.out.println(json);
```

then, outputs below string.

``` json
{
  "hello" : "jsonsmith",
  "items" : [
    {
      "key" : 1,
      "value" : "item-0-value"
    },
    null,
    {
      "key" : 2,
      "value" : "item-1-value"
    }
  ],
  "set" : {
    "very" : {
      "deep" : {
        "property" : {
          "directly" : true
        }
      }
    }
  }
}
```
