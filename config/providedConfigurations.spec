[
  // Use https://jolt-demo.appspot.com/#inception to develop/test
  // any changes to this file

  {
    // This section extracts configuration-id from the GENERIC-RESOURCES-API
    // response from sdnc
    "operation": "shift",
    "spec": {
      "service": {
        "*": {
          "service-data": {
            "provided-configurations": {
              "provided-configuration": {
                "*": {
                  "configuration-id": "configuration-id[&1]"
                }
              }
            }
          }
        }
      }
    }
  }
  ]