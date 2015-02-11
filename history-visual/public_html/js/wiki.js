var wiki = {
    
    persons: {},
    nodeHash: {},
    
    show : function() {
        d3.select("svg").selectAll("*").remove();
        d3.select("#details").selectAll("*").remove();
        d3.select("#controls").selectAll("*").remove();
        d3.select("#data").selectAll("*").remove();
        
        wiki.showControls();
    },
    
    showControls : function() {
        d3.select("#controls").append("input").attr("id", "wikiPage").attr("type", "text").style("width", "300px").attr("value", "Louis_I_of_Hungary");
        d3.select("#controls").append("button").attr("id", "go").on("click", wiki.buttonClick).html("Go");
        
        var select = d3.select("#controls").append("select").attr("id", "dropdown");
        select.on("change", function(d) {
            var value = d3.select(this).property("value");
            wiki.dropdownChanged(value);
        })

        d3.text("wiki-modal-details.html", function(data) {
            d3.select("#data").append("div").attr("id", "details").html(data);
        });
        
        d3.text("node-contextmenu.html", function(data) {
            d3.select("#details").append("div").attr("id", "node-contextmenu").html(data);
        });
        
        d3.text("svg-contextmenu.html", function(data) {
            d3.select("#details").append("div").attr("id", "svg-contextmenu").html(data);
        });
    },
    
    buttonClick : function() {
        d3.select("#dropdown").selectAll("*").remove();
        
        wikiPage = document.getElementById("wikiPage").value;
        
        d3.select("svg").selectAll("*").remove();
        //wiki.query1(wikiPage);
        wiki.queryFull(wikiPage);
    },
    
    dropdownChanged : function(value) {
        d3.select("#dropdown").selectAll("*").remove();
        
        d3.select("svg").selectAll("*").remove();
        wiki.queryFull(value);
    },
    
    /*query : function(wikiPage) {
        queue()
            .defer(d3.csv, "http://localhost:8080/history/wiki/person/" + wikiPage + "/nodes")
            .defer(d3.csv, "http://localhost:8080/history/wiki/person/" + wikiPage + "/edges")
            .await(function(error, file1, file2) {
                wiki.dataViz(file1, file2);
            });
    },*/
    
    /*
    query1 : function(wikiPage, callback) {
        d3.csv("http://localhost:8080/history/wiki/person/" + wikiPage + "/nodes", function(nodes) {
            wiki.query2(wikiPage, nodes, callback);
        })
    },
    
    query2 : function(wikiPage, nodes, callback) {
        d3.csv("http://localhost:8080/history/wiki/person/" + wikiPage + "/edges", function(edges) {
            if (callback!=null) {
                callback(nodes, edges)
            } else {
                wiki.dataViz(nodes, edges);
            }
        })
    },
    */
    
    queryFull : function(wikiPage, callback) {
        d3.json("http://localhost:8080/history/wiki/person/" + wikiPage + "/details", function(person) {
            wiki.persons[person.wikiPage] = person;
            
            nodes = new Array();
            nodesShort = new Array();
            nodes.push({id: person.wikiPage, name: person.name, birth: person.dateOfBirth, death: person.dateOfDeath, gender: "UNKNOWN", full: true});
            nodesShort.push({id: person.wikiPage, name: person.name, gender: "UNKNOWN"});
            nodes.push({id: person.father.wikiPage, name: person.father.name, birth: "", death: "", gender: "MALE"});
            nodesShort.push({id: person.father.wikiPage, name: person.father.name, gender: "MALE"});
            nodes.push({id: person.mother.wikiPage, name: person.mother.name, birth: "", death: "", gender: "FEMALE"});
            nodesShort.push({id: person.mother.wikiPage, name: person.mother.name, gender: "FEMALE"});
            
            for (i in person.spouses) {
                spouse = person.spouses[i];
                nodes.push({id: spouse.wikiPage, name: spouse.name, birth: "", death: "", gender: "UNKNOWN"});
                nodesShort.push({id: spouse.wikiPage, name: spouse.name, gender: "UNKNOWN"});
            }
            for (i in person.issues) {
                issue = person.issues[i];
                nodes.push({id: issue.wikiPage, name: issue.name, birth: "", death: "", gender: "UNKNOWN"});
                nodesShort.push({id: issue.wikiPage, name: issue.name, gender: "UNKNOWN"});
            }
            
            edges = new Array();
            edges.push({source: person.wikiPage, target: person.father.wikiPage, type: "HAS_FATHER"});
            edges.push({source: person.wikiPage, target: person.mother.wikiPage, type: "HAS_MOTHER"});
            for (i in person.spouses) {
                spouse = person.spouses[i];
                edges.push({source: person.wikiPage, target: spouse.wikiPage, type: "IS_SPOUSE_OF"});
            }
            for (i in person.issues) {
                issue = person.issues[i];
                edges.push({source: person.wikiPage, target: issue.wikiPage, type: "HAS_ISSUE"});
            }
            
            if (callback != null) {
                wiki.processNodes(nodes, edges, false);
                callback(nodesShort, edges);
            } else {
                wiki.processNodes(nodes, edges, true);
                wiki.dataViz(nodesShort, edges);
            }
        });
    },
    
    processNodes : function(nodes, edges, isNew) {
        if (isNew) {
            wiki.nodeHash = {};
        }
        
        for (x in nodes) {
            node = nodes[x];
            
            if (!wiki.nodeHash.hasOwnProperty(node.id)) {
                wiki.nodeHash[node.id] = node;
            } else if (node.hasOwnProperty('full')) {
                wiki.nodeHash[node.id] = node;
            } 
            
            if (wiki.nodeHash[node.id].gender == "UNKNOWN") {
                wiki.nodeHash[node.id].gender = node.gender;
            }
        }
    },
    
    // visualization
    
    dataViz : function(nodes, edges) {
        var context = null;
        populateEdges(nodes, edges);
        init();
        showGraph(nodes, edges);
        force.start();


        // functions
        
        function init() {
            var weightScale = d3.scale.linear().domain(d3.extent(edges, function(d) {return d.weight})).range([.1,1])
        
            force = d3.layout.force()
//              .charge(-1000)
                .charge(function (d) {return d.weight * -500})
                .gravity(.3)
                .linkDistance(50)
                .linkStrength(function (d) {return weightScale(d.weight)})
                .size([500,500])
                .nodes(nodes)
                .links(edges)
                .on("tick", forceTick);
            
            d3.select("svg")
                .on("contextmenu", function() {
                    showContextMenu(this, null, "svg");
                    d3.event.preventDefault();
                })
        }

        function populateEdges(nodes, edges) {
            nodeHash = {};
            for (x in nodes) {
                nodeHash[nodes[x].id] = nodes[x];
                nodes[x].selected=false;
            }
            for (x in edges) {
                if (edges[x].type == "IS_SPOUSE_OF") {
                    edges[x].weight = 5;
                } else {
                    edges[x].weight = 1;
                }
                edges[x].source = nodeHash[edges[x].source];
                edges[x].target = nodeHash[edges[x].target];
            }
        }

        // show graph

        function showGraph(nodes, edges) {
            link = d3.select("svg")
                    .selectAll("line.link")
                    .data(edges, function (d) {return d.source.id + "-" + d.target.id});
            
            link.enter()
                    .insert("line", ".node")
                    .attr("class", "link")
                    .style("stroke", "black")
                    .style("opacity", .5)
                    .style("stroke-width", function(d) {return d.weight})
            link.exit().remove;

            node = d3.select("svg")
                    .selectAll("g.node")
                    .data(nodes, function (d) {return d.id});
            
            node.enter()
                    .append("g")
                    .attr("class", "node")
                    .call(force.drag())
                    .on("click", fixNode)
                    .on("dblclick", selectNode)
                    .on("contextmenu", function(d,i) {
                        showContextMenu(this, d, "node")
                        d3.event.preventDefault();
                    })
                    .on("mouseover", function() {
                        d3.select(this)
                            .style("stroke", "black")
                            .style("stroke-width", "1px")
                            .style("cursor", "hand")
                    })
                    .on("mouseleave", function() {
                        d3.select(this)
                            .style("stroke", "black")
                            .style("stroke-width", "0px")
                            .style("cursor", "arrow")
                    })

            node.append("circle")
                    .attr("r", 12)
                    .style("fill", function(d) {
                        gender = d.gender;
                        if (gender == "MALE") return "BLUE";
                        else if (gender == "FEMALE") return "RED";
                        else return "GREY";
                    })
                    .style("stroke", "black")
                    .style("stroke-width", "1px")

            node.append("text")
                    .style("text-anchor", "middle")
                    .style("font-size", "75%")
                    .attr("y", 20)
                    .text(function(d) {return d.name})
            
            node.exit().remove;
        }

        // left click, double click -> select, fix
        
        function selectNode(d) {
            if (!d.selected) {
                d3.select(this).select("circle").classed({'node-selected': true}).style("stroke-width", 3);
                d.selected = true;
            } else {
                d3.select(this).select("circle").classed({'node-selected': false}).style("stroke-width", 1);
                d.selected = false;
            }
        }
      
        function fixNode(d) {
            if (!d.fixed) {
                d3.select(this).select("circle");
                d.fixed = true;
            } /*else {
                d3.select(this).select("circle");
                d.fixed = false;
            }*/
        }
        
        // right click -> context menu

        function showContextMenu(that, d, newContext) {
            if (context) {
                if (context !== newContext) {
//                    console.log('contextmenu: cannot execute for context: ' + newContext + ' while in current context: ' + context);
                    return;
                }
            }
            context = newContext;
            d3.event.preventDefault();
          
            if (context == "svg") {
                showSvgContextMenu()
            } else if (context == "node") {
                showNodeContextMenu(that, d)
            }
        }

        function showSvgContextMenu() {
            d3.event.preventDefault();
            
            var position = d3.mouse(d3.select("body")[0][0]);
            d3.select('#svg-context-menu')
                .style('position', 'absolute')
                .style('left', position[0] + "px")
                .style('top', position[1] + "px")
                .style('display', 'inline-block')
                .on('mouseleave', function() {
                    d3.select('#svg-context-menu')
                        .style('display', 'none')
                        .style("cursor", "arrow")
                    context = null;
                })
                
            d3.select("#svg-context-menu")
                    .selectAll("li")
                    .on("click", function() {
                        var allSelected = d3.selectAll("circle.node-selected");
                        for (var selected in allSelected) {
                            if (allSelected.hasOwnProperty(selected)) {
                                console.log(allSelected[selected])
                                selectedNodes = allSelected[selected];
                                for (var selectedNode in selectedNodes) {
                                    if (selectedNodes.hasOwnProperty(selectedNode) && ("__data__" in selectedNodes[selectedNode])) {
                                        hideNode(selectedNodes[selectedNode].__data__)
                                    }
                                }
                            }
                        }
                    }
            )
        }

        function showNodeContextMenu(that, d) {
            d3.event.preventDefault();

//            console.log(d3.select("svg")[0][0])

            var position = d3.mouse(d3.select("body")[0][0]);
            d3.select('#node-context-menu')
                .style('position', 'absolute')
                .style('left', (position[0]+20) + "px")
                .style('top', (position[1]+20) + "px")
                .style('display', 'inline-block')
                .on('mouseover', function() {
                    d3.select('#node-context-menu')
                        .style("cursor", "hand")
                })
                .on('mouseleave', function() {
                    d3.select('#node-context-menu')
                        .style('display', 'none')
                        .style("cursor", "arrow")
                    context = null;
                })
                
            d3.select("#node-context-menu")
                    .selectAll("li")
                    .on("click", function() {
                        if (this.id == "open") {
                            d3.select('#node-context-menu')
                                .style('display', 'none')
                                .style("cursor", "arrow")
                            
                            wiki.dropdownChanged(d.id)
                        } else if (this.id == "details") {
//                            d3.selectAll("td.data")
//                                    .data(d3.values(wiki.nodeHash[d.id]))
//                                    .html(function(p) {
//                                        return p
//                                    });
                            person = wiki.nodeHash[d.id];    
                            personFull = wiki.persons[d.id];
                            d3.select("td.data-id").html(function() {return person.id});
                            d3.select("td.data-name").html(function() {return person.name});
                            d3.select("td.data-birth").html(function() {return person.birth});
                            d3.select("td.data-death").html(function() {return person.death});
                            d3.select("td.data-gender").html(function() {return person.gender});
                            if (personFull != null) {
                                d3.selectAll("table.data-house")
                                        .selectAll("tr")
                                        .data(personFull.houses)
                                        .enter()
                                        .append("tr")
                                        .append("td")
                                        .html(function(h) {return h.name});
                            } else {
                                d3.selectAll("table.data-house")
                                        .selectAll("tr")
                                        .data([])
                                        .exit()
                                        .remove();
                            } 
                        } else if (this.id == "hide") {
                            hideNode(d)
                        } else if (this.id == "extract") {
                            extractNode(d)
                        } else if (this.id == "towiki") {
                            window.open("http://en.wikipedia.org/wiki/" + d.id)
                        }
                        
                        d3.select("#node-context-menu").style('display', 'none');
                    })
        }

        // hide, extract
        
        function hideNode(node) {
            force.stop();
            
            var originalNodes = force.nodes();
            var originalLinks = force.links();
            
            var influentialNodes = originalNodes.filter(function (d) {
                return d != node;
            });
            
            var influentialLinks = originalLinks.filter(function (d) {
                return influentialNodes.indexOf(d.source) > -1 &&
                        influentialNodes.indexOf(d.target) > -1;
            });
            
            d3.selectAll("g.node")
                .data(influentialNodes, function (d) {return d.id})
                .exit()
                .transition()
                .duration(1000)
                .style("opacity", 0)
                .remove();
            
            d3.selectAll("line.link")
                .data(influentialLinks, function (d) {
                    return d.source.id + "-" + d.target.id;
                })
                .exit()
                .transition()
                .duration(1000)
                .style("opacity", 0)
                .remove();
            
            force
                .nodes(influentialNodes)
                .links(influentialLinks);
            
            force.start();
        }
        
        function extractNode(node) {
            wiki.queryFull(node.id, extractNodeCallback)
        }
        
        function extractNodeCallback(nodes, edges) {
//            force.stop();
         
            populateEdges(nodes, edges);
            
            var oldEdges = force.links();
            var oldNodes = force.nodes();
            
            for (i in oldNodes) {
                oldNodes[i].gender = wiki.nodeHash[oldNodes[i].id].gender;
            }
            
            var newNodes = nodes.filter(function(node) {
                for (var oldNode in oldNodes) {
                    if (node.id == oldNodes[oldNode].id) {
                        return false;
                    }
                }
                
                oldNodes.push(node)
                return true;
            })
            

            var oldNodeHash = {};
            for (x in oldNodes) {
                oldNodeHash[oldNodes[x].id] = oldNodes[x];
            }

            var newEdges = edges.filter(function(edge) {
                for (var i in oldEdges) {
                    if (isSameEdge(oldEdges[i], edge)) {
                        return false
                    }
                }
                
                edge.source = oldNodeHash[edge.source.id]
                edge.target = oldNodeHash[edge.target.id]
              
                oldEdges.push(edge);
                return true;
            })
            
//            force.links(oldEdges).nodes(oldNodes);
            
            showGraph(oldNodes, oldEdges)
            
            force.start()
        }

        function isSameEdge(edge1, edge2) {
            es1 = edge1.source.id;
            et1 = edge1.target.id;
            es2 = edge2.source.id;
            et2 = edge2.target.id;
            
            return ((es1+et1) == (es2+et2)) || ((es1+et1) == (et2+es2)) ;
        }

        // force

        function forceTick() {
            d3.selectAll("line.link")
                .attr("x1", function (d) {return d.source.x;})
                .attr("x2", function (d) {return d.target.x;})
                .attr("y1", function (d) {return d.source.y;})
                .attr("y2", function (d) {return d.target.y;});

            d3.selectAll("g.node")
                .attr("transform", function (d) {return "translate("+d.x+","+d.y+")";});
        }
    }
}