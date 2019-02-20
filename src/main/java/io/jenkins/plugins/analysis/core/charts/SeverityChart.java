package io.jenkins.plugins.analysis.core.charts;

import edu.hm.hafner.analysis.Severity;

import io.jenkins.plugins.analysis.core.graphs.ChartModelConfiguration;
import io.jenkins.plugins.analysis.core.graphs.LinesChartModel;
import io.jenkins.plugins.analysis.core.graphs.SeveritySeriesBuilder;
import io.jenkins.plugins.analysis.core.util.LocalizedSeverity;
import io.jenkins.plugins.analysis.core.util.StaticAnalysisRun;

/**
 * Builds the model for a graph showing all issues by severity.
 *
 * @author Ullrich Hafner
 */
public class SeverityChart {
    /**
     * Creates the chart for the specified results.
     *
     * @param results
     *         the analysis results to render
     *
     * @return the chart model
     */
    public LineModel create(final Iterable<? extends StaticAnalysisRun> results) {
        SeveritySeriesBuilder builder = new SeveritySeriesBuilder();
        LinesChartModel lineModel = builder.createDataSet(createConfiguration(), results);

        LineSeries high = createSeries(Severity.WARNING_HIGH);
        high.addAll(lineModel.getValues(Severity.WARNING_HIGH.getName()));
        LineSeries normal = createSeries(Severity.WARNING_NORMAL);
        normal.addAll(lineModel.getValues(Severity.WARNING_NORMAL.getName()));
        LineSeries low = createSeries(Severity.WARNING_LOW);
        low.addAll(lineModel.getValues(Severity.WARNING_LOW.getName()));

        LineModel model = new LineModel();
        model.addSeries(low, normal, high);
        model.addXAxisLabels(lineModel.getXLabels());

        return model;
    }

    private ChartModelConfiguration createConfiguration() {
        return new ChartModelConfiguration();
    }

    private LineSeries createSeries(final Severity severity) {
        return new LineSeries(LocalizedSeverity.getLocalizedString(severity),
                SeverityPalette.getColor(severity).getNormal());
    }
}
