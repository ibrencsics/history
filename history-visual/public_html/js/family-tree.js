var familyTree = {
    
    show : function() {
        d3.select("svg").selectAll("*").remove();
        d3.select("#details").selectAll("*").remove();
        d3.json("http://localhost:8080/history/wiki/person/Henry_VIII_of_England/",function(error,data) {familyTree.dataViz(data)});
    },
    
    dataViz : function(data) {
        var dataList = [data];
        
        d3.select("svg")
            .append("line").attr("x1",50).attr("y1",100).attr("x2",100).attr("y2",150)
            .style("stroke", "black");
        
        d3.select("svg")
            .append("g") 
            .attr("id", "mainPerson")
            .attr("transform", "translate(50,100)")
            .selectAll("g")
            .data(dataList)
            .enter()
            .append("g")
            .attr("class", "mainPersonG")
            .attr("transform", function (d,i) {return "translate(" + (i * 100) + ", 0)"} ); 
            
        d3.select("svg")    
            
        d3.select("svg")
            .append("g") 
            .attr("id", "spouse")
            .attr("transform", "translate(50,100)")
            .selectAll("g")   
            .data(data.spouses)
            .enter()
            .append("g")
            .attr("class", "spouseG")
            .attr("transform", function (d,i) {return "translate(" + ((i+1) * 100) + ", 0)"} ); 
    
    
        var mainG = d3.selectAll("g.mainPersonG");
        mainG
            .append("circle")
            .attr("r", 20)
            .style("fill", "pink")
            .style("stroke", "black")
            .style("stroke-width", "1px");
        mainG
            .append("text")
            .style("text-anchor", "middle")
            .attr("y", 30)
            .style("font-size", "10px")
            .text(function(d) {return d.name;});
    
        var spouseG = d3.selectAll("g.spouseG");
        spouseG
            .append("circle")
            .attr("r", 20)
            .style("fill", "pink")
            .style("stroke", "black")
            .style("stroke-width", "1px");
        spouseG
            .append("text")
            .style("text-anchor", "middle")
            .attr("y", 30)
            .style("font-size", "10px")
            .text(function(d) {return d.localPart;});
        
        
                
    }
}

