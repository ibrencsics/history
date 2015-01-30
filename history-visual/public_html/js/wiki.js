var wiki = {
    
    show : function() {
        d3.select("svg").selectAll("*").remove();
        d3.select("#details").selectAll("*").remove();
        d3.select("#controls").selectAll("*").remove();
        d3.select("#data").selectAll("*").remove();
        
        wiki.showControls();
        
        //wiki.query("William_the_Conqueror");
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
    },
    
    buttonClick : function() {
        d3.select("#dropdown").selectAll("*").remove();
        
        wikiPage = document.getElementById("wikiPage").value;
        console.log(wikiPage);
        
        d3.select("svg").selectAll("*").remove();
        wiki.query1(wikiPage);
    },
    
    dropdownChanged : function(value) {
        d3.select("#dropdown").selectAll("*").remove();
        
        d3.select("svg").selectAll("*").remove();
        wiki.query1(value);
    },
    
    /*query : function(wikiPage) {
        queue()
            .defer(d3.csv, "http://localhost:8080/history/wiki/person/" + wikiPage + "/nodes")
            .defer(d3.csv, "http://localhost:8080/history/wiki/person/" + wikiPage + "/edges")
            .await(function(error, file1, file2) {
                wiki.dataViz(file1, file2);
            });
    },*/
    
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
    
    // visualization
    
    dataViz : function(nodes, edges) {
        var nodeHash = {};
        for (x in nodes) {
            nodeHash[nodes[x].id] = nodes[x];
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
        
        var weightScale = d3.scale.linear().domain(d3.extent(edges, function(d) {return d.weight})).range([.1,1])
        
        force = d3.layout.force()
//          .charge(-1000)
            .charge(function (d) {return d.weight * -500})
            .gravity(.3)
            .linkDistance(50)
            .linkStrength(function (d) {return weightScale(d.weight)})
            .size([500,500]).nodes(nodes)
            .links(edges).on("tick", forceTick);

        showGraph(nodes, edges);
      
        d3.select("#dropdown")
                .selectAll("option")
                .data(nodes)
                .enter()
                .append("option")
                .text(function (d) {return d.id})
      
        d3.text("wiki-contextmenu.html", function(data) {
            d3.select("#details").append("div").attr("id", "contextmenu").html(data);
        });
        
//      d3.selectAll("line").attr("marker-end", "url(#Triangle)");
        
        force.start();


        // functions

        // show graph

        function showGraph(nodes, edges) {
            d3.select("svg").selectAll("line.link").data(edges, function (d) {return d.source.id + "-" + d.target.id}).enter()
                .append("line")
                .attr("class", "link")
                .style("stroke", "black")
                .style("opacity", .5)
                .style("stroke-width", function(d) {return d.weight})

            var nodeEnter = d3.select("svg").selectAll("g.node").data(nodes, function (d) {return d.id}).enter()
                .append("g")
                .attr("class", "node")
                .call(force.drag())
                .on("click", fixNode)
                .on("dblclick", releaseNode)
                .on("contextmenu", function(d,i) {
                    showContextMenu(this, d, i);
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

            nodeEnter.append("circle")
                .attr("r", 12)
                .style("fill", function(d) {
                    gender = d.gender;
                    if (gender == "MALE") return "BLUE";
                    else if (gender == "FEMALE") return "RED";
                    else return "GREY";
                })
                .style("stroke", "black")
                .style("stroke-width", "1px")

            nodeEnter.append("text")
                .style("text-anchor", "middle")
                .style("font-size", "75%")
                .attr("y", 20)
                .text(function(d) {return d.name})
        }

        // left click -> fix, unfix
      
        function fixNode(d) {
            d3.select(this).select("circle").style("stroke-width", 3);
            d.fixed = true;
        }
        
        function releaseNode(d) {
            d3.select(this).select("circle").style("stroke-width", 1);
            d.fixed = false;
        }
        
        // right click -> context menu

        function showContextMenu(that, d) {
            d3.event.preventDefault();

            var position = d3.mouse(that);
            
//            console.log(d3.select("svg")[0][0])

            var position = d3.mouse(d3.select("body")[0][0]);
            d3.select('#context-menu')
                .style('position', 'absolute')
                .style('left', (position[0]+20) + "px")
                .style('top', (position[1]+20) + "px")
                .style('display', 'inline-block')
                .on('mouseover', function() {
                    d3.select('#context-menu')
                        .style("cursor", "hand")
                })
                .on('mouseleave', function() {
                    d3.select('#context-menu')
                        .style('display', 'none')
                        .style("cursor", "arrow")
                })
                
            d3.select("#context-menu")
                    .selectAll("li")
                    .on("click", function() {
                        if (this.id == "open") {
                            d3.select('#context-menu')
                                .style('display', 'none')
                                .style("cursor", "arrow")
                            
                            wiki.dropdownChanged(d.id)
                        } else if (this.id == "details") {
                            d3.selectAll("td.data")
                                    .data(d3.values(d))
                                    .html(function(p) {
                                        return p
                                    });
                        } else if (this.id == "hide") {
                            hideNode(d)
                        } else if (this.id == "extract") {
                            extractNode(d)
                        }
                        
                        d3.select("#context-menu").style('display', 'none');
                    })
        }

        // hide, extract
        
        function hideNode(node) {
            force.stop();
            
            var originalNodes = force.nodes(); //#a
            var originalLinks = force.links();
            
            var influentialNodes = originalNodes.filter(function (d) {
                return d != node;
            });
            
            var influentialLinks = originalLinks.filter(function (d) {
                return influentialNodes.indexOf(d.source) > -1 &&
                        influentialNodes.indexOf(d.target) > -1;
            }); //#b
            
            d3.selectAll("g.node")
                .data(influentialNodes, function (d) {return d.id})
                .exit()
                .transition() //#c
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
            wiki.query1(node.id, extractNodeCallback)
        }
        
        function extractNodeCallback(nodes, edges) {
            force.stop();
         
            var nodeHash = {};
            for (x in nodes) {
                nodeHash[nodes[x].id] = nodes[x];
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
            
            var oldEdges = force.links();
            var oldNodes = force.nodes();
            
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
            
            force.links(oldEdges).nodes(oldNodes);
            
            showGraph(oldNodes, oldEdges)
            
            force.start()
        }

        function isSameEdge(edge1, edge2) {
            es1 = edge1.source.id;
            et1 = edge1.target.id;
            es2 = edge2.source.id;
            et2 = edge2.target.id;
            
            return ((es1+et1) == (es2+et2)) || ((es1+et1) == (et2+es2)) 
        }

        // force

        function forceTick() {
            d3.selectAll("line.link")
                .attr("x1", function (d) {return d.source.x})
                .attr("x2", function (d) {return d.target.x})
                .attr("y1", function (d) {return d.source.y})
                .attr("y2", function (d) {return d.target.y});

            d3.selectAll("g.node")
                .attr("transform", function (d) {return "translate("+d.x+","+d.y+")"})
        }
    }
}