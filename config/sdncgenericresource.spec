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
                                          "*": {
                                            "@": "vfList[&14].vfModuleList[&10].vmList[&1].name",
                                            "@(3,vm-type)": "vfList[&14].vfModuleList[&10].vmList[&1].nfNamingCode",
                                            "@(3,inMaint)": {
                                              "#lockedBoolean": "vfList[&15].vfModuleList[&11].vmList[&2].attributeList[0].name",
                                              "yes": {
                                                "#true": "vfList[&16].vfModuleList[&12].vmList[&3].attributeList[0].value"
                                              },
                                              "no": {
                                                "#false": "vfList[&16].vfModuleList[&12].vmList[&3].attributeList[0].value"
                                              }
                                            },
                                            "@(3,prov-status)": {
                                              "#provStatus": "vfList[&15].vfModuleList[&11].vmList[&2].attributeList[1].name",
                                              "@(4,prov-status)": "vfList[&15].vfModuleList[&11].vmList[&2].attributeList[1].value"
                                            },
                                            "@(3,pserver)": {
                                              "#hostName": "vfList[&15].vfModuleList[&11].vmList[&2].attributeList[2].name",
                                              "hostname": "vfList[&15].vfModuleList[&11].vmList[&2].attributeList[2].value"
                                            },
                                            "@(3,image)": {
                                              "#imageId": "vfList[&15].vfModuleList[&11].vmList[&2].attributeList[3].name",
                                              "image-name": "vfList[&15].vfModuleList[&11].vmList[&2].attributeList[3].value"
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
                        "network-name": "vfList[0].vfModuleList[0].networkList[&4].name",
                        "isShared": {
                          "#sharedNetworkBoolean": "vfList[0].vfModuleList[0].networkList[&5].attributeList[0].name",
                          "yes": {
                            "#true": "vfList[0].vfModuleList[0].networkList[&6].attributeList[0].value"
                          },
                          "no": {
                            "#false": "vfList[0].vfModuleList[0].networkList[&6].attributeList[0].value"
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
  ]
