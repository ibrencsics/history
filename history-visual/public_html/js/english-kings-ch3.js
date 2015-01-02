var englishKings = {
    
    show : function() {
        d3.csv("data/english-kings.csv", function(data) {
            englishKings.dataViz(data);
        })
    },
    
    dataViz : function(data) {
        
        d3.select("svg")
            .append("g")
            .attr("id", "teamsG")
            .attr("transform", "translate(50,100)")
            .selectAll("g")
            .data(data)
            .enter()
            .append("g")
            .attr("class", "overallG")
            .attr("transform",
            function (d,i) {return "translate(" + ((i%10) * 100) + ", " + (Math.floor(i/10) * 70) + ")"}
            );

        var teamG = d3.selectAll("g.overallG");
        
        teamG
            .append("circle")
            .attr("r", 20)
            .style("fill", "pink")
            .style("stroke", "black")
            .style("stroke-width", "1px");
        teamG
            .append("text")
            .style("text-anchor", "middle")
            .attr("y", 30)
            .style("font-size", "10px")
            .text(function(d) {return d.name;});
    }
}