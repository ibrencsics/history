var wiki = {
    
    persons: {},
    nodeHash: {},
    
    show : function() {
        d3.select("svg").selectAll("*").remove();
//        d3.select("#wikiBrowser").selectAll("*").remove();
        d3.select("#controls").selectAll("*").remove();
        d3.select("#data").selectAll("*").remove();
        
        wiki.showControls();
    },
    
    showControls : function() {
        d3.select("#controls")
                .append("input").attr("id", "wikiPage").attr("type", "text").style("width", "300px")
                .attr("value", "Ludwig_I,_King_of_Bavaria")
                .on("keyup", function() {
                    if (d3.event.keyCode == 13) {
                        wiki.buttonClick();
                    }
                });
        d3.select("#controls")
                .append("button").attr("id", "go").html("Go")
                .on("click", wiki.buttonClick);
        
//        var select = d3.select("#controls").append("select").attr("id", "dropdown");
//        select.on("change", function(d) {
//            var value = d3.select(this).property("value");
//            wiki.dropdownChanged(value);
//        })

        d3.text("wiki-modal-details.html", function(data) {
            d3.select("#data").append("div").attr("id", "details").html(data);
        });
        
        d3.text("node-contextmenu.html", function(data) {
            d3.select("#wikingDiv").append("div").attr("id", "node-contextmenu").html(data);
        });
        
        d3.text("svg-contextmenu.html", function(data) {
            d3.select("#wikingDiv").append("div").attr("id", "svg-contextmenu").html(data);
        });
    },
    
    buttonClick : function() {
//        d3.select("#dropdown").selectAll("*").remove();
        wikiPage = document.getElementById("wikiPage").value;
        d3.select("svg").selectAll("*").remove();
        wiki.queryFull(wikiPage);
    },
    
    deleteAndQueryFull : function(value) {
        d3.json("http://localhost:8080/history/wiki/person/" + value + "/delete", function() {
            wiki.cleanAndQueryFull(value);
        });
    },
    
    cleanAndQueryFull : function(value) {
//        d3.select("#dropdown").selectAll("*").remove();
        d3.select("svg").selectAll("*").remove();
        wiki.queryFull(value);
    },
    
    queryFull : function(wikiPage, callback) {
        d3.json("http://localhost:8080/history/wiki/person/" + wikiPage + "/details", function(error, person) {
            if (error) {
                window.alert("Parsing error");
                return;
            }
            
            wiki.persons[person.wikiPage] = person;
            
            nodes = new Array();
            nodes.push({id: person.wikiPage, name: person.name, gender: person.gender, full: true});
            nodes.push({id: person.father.wikiPage, name: person.father.name, birth: person.father.dateOfBirth, death: person.father.dateOfDeath, gender: person.father.gender});
            nodes.push({id: person.mother.wikiPage, name: person.mother.name, birth: person.mother.dateOfBirth, death: person.mother.dateOfDeath, gender: person.mother.gender});
            
            for (i in person.spouses) {
                spouse = person.spouses[i];
                nodes.push({id: spouse.wikiPage, name: spouse.name, birth: spouse.dateOfBirth, death: spouse.dateOfDeath, gender: spouse.gender});
            }
            for (i in person.issues) {
                issue = person.issues[i];
                nodes.push({id: issue.wikiPage, name: issue.name, birth: issue.dateOfBirth, death: issue.dateOfDeath, gender: issue.gender});
            }
            
            edges = new Array();
            
            edges.push({source: person.wikiPage, target: person.father.wikiPage, type: "HAS_FATHER"});
            edges.push({source: person.wikiPage, target: person.mother.wikiPage, type: "HAS_MOTHER"});
            edges.push({source: person.father.wikiPage, target: person.mother.wikiPage, type: "IS_SPOUSE_OF"});
            
            for (i in person.spouses) {
                spouse = person.spouses[i];
                edges.push({source: person.wikiPage, target: spouse.wikiPage, type: "IS_SPOUSE_OF"});
            }
            for (i in person.issues) {
                issue = person.issues[i];
                edges.push({source: issue.wikiPage, target: person.wikiPage, type: "HAS_ISSUE"});
            }
            
            if (callback != null) {
                wiki.processNodes(nodes, edges, false);
                callback(nodes, edges);
            } else {
                wiki.processNodes(nodes, edges, true);
                wiki.dataViz(nodes, edges);
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
                .gravity(.2)
                .linkDistance(50)
                .linkStrength(function (d) {return weightScale(d.weight)})
                .size([1200,500])
                .nodes(nodes)
                .links(edges)
                .on("tick", forceTick);
            
            svg = d3.select("svg")
                .on("click", function() {
                    hideContextMenu();
                })
                .on("contextmenu", function() {
                    showContextMenu(this, null, "svg");
                    d3.event.preventDefault();
                })
                
            var marker = svg.append('defs')
                .append('marker')
                .attr("id", "Triangle")
                .attr("refX", 64)
                .attr("refY", 6)
                .attr("markerUnits", 'userSpaceOnUse')
                .attr("markerWidth", 12)
                .attr("markerHeight", 18)
                .attr("orient", 'auto')
                .append('path')
                .attr("d", 'M 0 0 12 6 0 12 3 6');    
        
//            treeZoom = d3.behavior.zoom();
//            treeZoom.on("zoom", zoomed);
//            d3.select("svg").call(treeZoom)

//            function zoomed() {
//                var zoomTranslate = treeZoom.translate();
//                console.log(zoomTranslate)
//                d3.select("g.node").attr("transform", "translate("+zoomTranslate[0]+","+zoomTranslate[1]+")")
//            }
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

            d3.selectAll("line")
                    .attr("marker-end", function(d) {
                        if (d.type == "HAS_FATHER" || d.type == "HAS_MOTHER" || d.type == "HAS_ISSUE") {
                            return "url(#Triangle)";
                        }
                    });

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
//                            .stylce("stroke", "black")
//                            .style("stroke-width", "1px")
                            .style("cursor", "hand")
                    })
                    .on("mouseleave", function() {
                        d3.select(this)
//                            .style("stroke", "black")
//                            .style("stroke-width", "0px")
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
        
        function hideContextMenu() {
            hideSvgContextMenu();
            hideNodeContextMenu();
        }

        function showSvgContextMenu() {
            d3.event.preventDefault();
            
            var position = d3.mouse(d3.select("body")[0][0]);
            d3.select('#svg-context-menu')
                .style('position', 'absolute')
                .style('left', position[0] + "px")
                .style('top', position[1] + "px")
                .style('display', 'inline-block')
                .on('mouseleave', function() { hideSvgContextMenu(); })
                
            d3.select("#svg-context-menu")
                    .selectAll("li")
                    .on("click", function() {
                        var allSelected = d3.selectAll("circle.node-selected");
                        for (var selected in allSelected) {
                            if (allSelected.hasOwnProperty(selected)) {
                                selectedNodes = allSelected[selected];
                                for (var selectedNode in selectedNodes) {
                                    if (selectedNodes.hasOwnProperty(selectedNode) && ("__data__" in selectedNodes[selectedNode])) {
                                        hideNode(selectedNodes[selectedNode].__data__)
                                    }
                                }
                            }
                        }
                        
                        hideSvgContextMenu();
                    }
            )
        }
        
        function hideSvgContextMenu() {
            d3.select('#svg-context-menu')
                .style('display', 'none')
                .style("cursor", "arrow")
            context = null;
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
                .on('mouseleave', function() { hideNodeContextMenu(); })
                
            d3.select("#node-context-menu")
                    .selectAll("li")
                    .on("click", function() {
                        if (this.id == "open") {
                            d3.select('#node-context-menu')
                                .style('display', 'none')
                                .style("cursor", "arrow")
                            
                            wiki.cleanAndQueryFull(d.id)
                        } else if (this.id == "details") {
                            showData(d);
                        } else if (this.id == "hide") {
                            hideNode(d)
                        } else if (this.id == "extract") {
                            extractNode(d)
                        } else if (this.id == "towiki") {
                            window.open("http://en.wikipedia.org/wiki/" + d.id)
                        } else if (this.id == "delete") {
                            wiki.deleteAndQueryFull(d.id);
                        }
                        
//                        d3.select("#node-context-menu").style('display', 'none');
                        hideNodeContextMenu();
                    })
        }
        
        function hideNodeContextMenu() {
            d3.select('#node-context-menu')
                .style('display', 'none')
                .style("cursor", "arrow")
            context = null;
        }
        
        // data
        
        function showData(d) {
            person = wiki.nodeHash[d.id];    
            personFull = wiki.persons[d.id];
            
            d3.select("td.data-id").html(function() {return person.id});
            d3.select("td.data-name").html(function() {return person.name});
            d3.select("td.data-gender").html(function() {return person.gender});
            
            d3.selectAll("table.data-house").selectAll("tr").data([]).exit().remove();
            d3.selectAll("table.data-spouse").selectAll("tr").data([]).exit().remove();
            d3.selectAll("table.data-issue").selectAll("tr").data([]).exit().remove();
            d3.selectAll("tr.data-job").data([]).exit().remove();
            
            d3.select("td.data-birth").html(function(d) {
                return (personFull!=null && personFull.hasOwnProperty("dateOfBirth")) ? personFull.dateOfBirth : person.birth;
            });
            d3.select("td.data-death").html(function(d) {
                return (personFull!=null && personFull.hasOwnProperty("dateOfDeath")) ? personFull.dateOfDeath : person.death;
            });
            d3.select("td.data-father").html(function(d) {
                return (personFull!=null && personFull.hasOwnProperty("father")) ? personFull.father.name : "";
            });
            d3.select("td.data-mother").html(function(d) {
                return (personFull!=null && personFull.hasOwnProperty("mother")) ? personFull.mother.name : "";
            });
            
            if (personFull != null) {
                
                if (personFull.hasOwnProperty("houses")) {
                    d3.selectAll("table.data-house")
                            .selectAll("tr")
                            .data(personFull.houses)
                            .enter()
                            .append("tr")
                            .append("td")
                            .html(function(h) {return h.name});
                }
                
                if (personFull.hasOwnProperty("spouses")) {
                    d3.selectAll("table.data-spouse")
                            .selectAll("tr")
                            .data(personFull.spouses)
                            .enter()
                            .append("tr")
                            .append("td")
                            .html(function(s) {return s.name});
                }
                
                if (personFull.hasOwnProperty("issues")) {
                    d3.selectAll("table.data-issue")
                            .selectAll("tr")
                            .data(personFull.issues)
                            .enter()
                            .append("tr")
                            .append("td")
                            .html(function(s) {return s.name});
                }
                
                if (personFull.hasOwnProperty("jobs")) {
                    d3.select("table.data-jobs")
                            .selectAll("tr.data-job")
                            .data(personFull.jobs)
                            .enter()
                            .append("tr")
                            .attr("class", "data-job")

                    d3.selectAll("tr.data-job")
                            .selectAll("td")
                            .data(function(d) {
                                countries = new Array();
                                d.countries.forEach(function(v,i,a) {countries.push(v.name);});
                                return [countries, d.from, d.to, d.title];
                            })
                            .enter()
                            .append("td")
                            .html(function(d) {
                                return d;
                            });
                }
                        
            }   
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