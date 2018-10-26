[
  // Use https://jolt-demo.appspot.com/#inception to develop/test
  // any changes to this file

  {
    // This section converts the GENERIC-RESOURCES-API response from sdnc
    // to org.onap.pomba.common.datatypes.ModelContext
    "operation": "shift",
    "spec": {
      "service": {
        "*": {
          "service-data": {
            "service-topology": {
              "service-topology-identifier": {
                "service-instance-id": "service.uuid",
                "service-instance-name": "service.name"
              },
              "onap-model-information": {
                "model-invariant-uuid": "service.invariantUUID"
              }
            },
            "vnfs": {
              "vnf": {
                "*": {
                  "vnf-data": {
                    "vnf-topology": {
                      "onap-model-information": {
                        "model-uuid": "vfList[&4].uuid",
                        "model-invariant-uuid": "vfList[&4].invariantUUID"
                      },
                      "vnf-topology-identifier-structure": {
                        "vnf-type": "vfList[&4].type",
                        "vnf-name": "vfList[&4].name"
                      }
                    },
                    "vf-modules": {
                      "vf-module": {
                        "*": {
                          "vf-module-data": {
                            "vf-module-topology": {
                              "vf-module-topology-identifier": {
                                "vf-module-name": "vfList[&8].vfModuleList[&4].name"
                              },
                              "onap-model-information": {
                                "model-uuid": "vfList[&8].vfModuleList[&4].uuid",
                                "model-invariant-uuid": "vfList[&8].vfModuleList[&4].invariantUUID"
                              },
                              "vf-module-assignments": {
                                "vms": {
                                  "vm": {
                                    "*": {
                                      "vm-names": {
                                        "vm-name": {
                                          "@(2,vm-type)": "vfList[&13].vfModuleList[&9].vmList[&3].nfNamingCode",
                                          "*": "vfList[&13].vfModuleList[&9].vmList[&3].name"
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            },
            "networks": {
              "network": {
                "*": {
                  "network-data": {
                    "network-topology": {
                      "onap-model-information": {
                        "model-uuid": "vfList[0].vfModuleList[0].networkList[&4].uuid",
                        "model-invariant-uuid": "vfList[0].vfModuleList[0].networkList[&4].invariantUUID"
                      },
                      "network-topology-identifier-structure": {
                        "network-type": "vfList[0].vfModuleList[0].networkList[&4].type",
                        "network-name": "vfList[0].vfModuleList[0].networkList[&4].name"
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  ]
