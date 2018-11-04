[
  // Use https://jolt-demo.appspot.com/#inception to develop/test
  // any changes to this file

  {
    // This section converts aai response for GET VNF-API from SDNC
    // to org.onap.pomba.common.datatypes.VF
    "operation": "shift",
    "spec": {
      "vnfs": {
        "vnf-list": {
          "*": {
            "*": "vnf-list[&1].&"
          }
        }
      }
    }
  }
  ]
