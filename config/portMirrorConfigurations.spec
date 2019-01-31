[
  // Use https://jolt-demo.appspot.com/#inception to develop/test
  // any changes to this file

  {
    // This section extracts pnf-information from the GENERIC-RESOURCES-API
    // response from sdnc
    "operation": "shift",
    "spec": {
      "port-mirror-configuration-request-input": {
        "source-port": {
          "*": {
            "pnf-information": {
              "pnf-name": "pnfList[0].name",
              "#nfType": "pnfList[0].attributeList[0].name",
              "pnf-type": "pnfList[0].attributeList[0].value"
            }
          }
        },
        "dest-port": {
          "*": {
            "pnf-information": {
              "pnf-name": "pnfList[1].name",
              "#nfType": "pnfList[1].attributeList[0].name",
              "pnf-type": "pnfList[1].attributeList[0].value"
            }
          }
        }
      }
    }
    }
  ]
