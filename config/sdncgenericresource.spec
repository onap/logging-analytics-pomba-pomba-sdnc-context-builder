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
                "model-invariant-uuid": "service.modelInvariantUUID",
                "model-uuid": "service.modelVersionID"
              }
            },
            "vnfs": {
              "vnf": {
                "*": {
                  "vnf-data": {
                    "vnf-topology": {
                      "onap-model-information": {
                        "model-uuid": "vnfList[&4].modelVersionID",
                        "model-invariant-uuid": "vnfList[&4].modelInvariantUUID"
                      },
                      "vnf-topology-identifier-structure": {
                        "vnf-id": "vnfList[&4].uuid",
                        "vnf-type": "vnfList[&4].type",
                        "vnf-name": "vnfList[&4].name"
                      },
                      "vnf-resource-assignments": {
                        "vnf-networks": {
                          "vnf-network": {
                            "*": {
                              "network-name": "vnfList[&7].networkList[&1].name",
                              "network-id": "vnfList[&7].networkList[&1].uuid",
                              "#networkRole": "vnfList[&7].networkList[&1].attributeList[0].name",
                              "network-role": "vnfList[&7].networkList[&1].attributeList[0].value"
                            }
                          }
                        }
                      }
                    },
                    "vf-modules": {
                      "vf-module": {
                        "*": {
                          "vf-module-data": {
                            "vf-module-topology": {
                              "vf-module-topology-identifier": {
                                "vf-module-id": "vnfList[&8].vfModuleList[&4].uuid",
                                "vf-module-name": "vnfList[&8].vfModuleList[&4].name"
                              },
                              "onap-model-information": {
                                "model-uuid": "vnfList[&8].vfModuleList[&4].modelVersionID",
                                "model-invariant-uuid": "vnfList[&8].vfModuleList[&4].modelInvariantUUID",
                                "model-customization-uuid": "vnfList[&8].vfModuleList[&4].modelCustomizationUUID"
                              },
                              "vf-module-assignments": {
                                "vms": {
                                  "vm": {
                                    "*": {
                                      "vm-names": {
                                        "vm-name": {
                                          "*": {
                                            "@": "vnfList[&14].vfModuleList[&10].vmList[&1].name",
                                            "@(3,vm-type)": "vnfList[&14].vfModuleList[&10].vmList[&1].nfNamingCode",
                                            "@(3,inMaint)": {
                                              "#lockedBoolean": "vnfList[&15].vfModuleList[&11].vmList[&2].attributeList[0].name",
                                              "yes": {
                                                "#true": "vnfList[&16].vfModuleList[&12].vmList[&3].attributeList[0].value"
                                              },
                                              "no": {
                                                "#false": "vnfList[&16].vfModuleList[&12].vmList[&3].attributeList[0].value"
                                              }
                                            },
                                            "@(3,prov-status)": {
                                              "#provStatus": "vnfList[&15].vfModuleList[&11].vmList[&2].attributeList[1].name",
                                              "@(4,prov-status)": "vnfList[&15].vfModuleList[&11].vmList[&2].attributeList[1].value"
                                            },
                                            "@(3,pserver)": {
                                              "#hostName": "vnfList[&15].vfModuleList[&11].vmList[&2].attributeList[2].name",
                                              "hostname": "vnfList[&15].vfModuleList[&11].vmList[&2].attributeList[2].value"
                                            },
                                            "@(3,image)": {
                                              "#imageId": "vnfList[&15].vfModuleList[&11].vmList[&2].attributeList[3].name",
                                              "image-name": "vnfList[&15].vfModuleList[&11].vmList[&2].attributeList[3].value"
                                            }
                                          }
                                        }
                                      },
                                      "vm-networks": {
                                        "vm-network": {
                                          "*": {
                                            "related-networks": {
                                              "related-network": {
                                                "*": {
                                                  "network-id": "vnfList[&17].vfModuleList[&13].networkList[&1].uuid",
                                                  "network-name": "vnfList[&17].vfModuleList[&13].networkList[&1].name",
                                                  "#networkRole": "vnfList[&17].vfModuleList[&13].networkList[&1].attributeList[0].name",
                                                  "network-role": "vnfList[&17].vfModuleList[&13].networkList[&1].attributeList[0].value"
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
                }
              }
            },
            "networks": {
              "network": {
                "*": {
                  "network-data": {
                    "network-topology": {
                      "network-topology-identifier-structure": {
                        "network-name": "networkList[&4].name",
                        "network-id": "networkList[&4].uuid",
                        "#networkRole": "networkList[&4].attributeList[0].name",
                        "network-role": "networkList[&4].attributeList[0].value",
                        "#networkType": "networkList[&4].attributeList[1].name",
                        "network-type": "networkList[&4].attributeList[1].value",
                        "#networkTechnology": "networkList[&4].attributeList[2].name",
                        "network-technology": "networkList[&4].attributeList[2].value"
                      },
                      "onap-model-information": {
                        "model-uuid": "networkList[&4].modelVersionID",
                        "model-invariant-uuid": "networkList[&4].modelInvariantUUID"
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
