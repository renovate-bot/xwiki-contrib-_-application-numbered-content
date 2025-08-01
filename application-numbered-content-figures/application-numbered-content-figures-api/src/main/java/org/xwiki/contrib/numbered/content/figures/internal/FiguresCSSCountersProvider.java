/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.numbered.content.figures.internal;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.numbered.content.common.CSSCountersProvider;
import org.xwiki.contrib.numbered.content.figures.NumberedFiguresException;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;

/**
 * Provider for the CSS counters used for figures numbering.
 *
 * @version $Id$
 * @since 1.10.3
 */
@Component
@Singleton
@Named("figures")
public class FiguresCSSCountersProvider implements CSSCountersProvider
{
    @Inject
    private NumberedFiguresDisplayDataManager numberedFiguresDisplayDataManager;

    @Inject
    private Logger logger;

    @Override
    public Set<String> selectors(Locale locale)
    {
        try {
            return this.numberedFiguresDisplayDataManager
                .getFigureDisplayData(locale)
                .getFigureCounters()
                .keySet()
                .stream()
                .map(key -> "cfigure-" + key)
                .collect(Collectors.toSet());
        } catch (NumberedFiguresException e) {
            this.logger.warn("Failed to initialize the CSS counters for figure numbering. Cause: [{}]",
                getRootCauseMessage(e));
            return Set.of();
        }
    }
}
