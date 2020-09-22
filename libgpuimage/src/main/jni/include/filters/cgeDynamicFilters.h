/*
 * cgeDynamicFilters.h
 *
 *  Created on: 2015-11-18
 *
 */

#ifndef _CGEDYNAMICFILTERS_H_
#define _CGEDYNAMICFILTERS_H_

#include "cgeDynamicWaveFilter.h"
#include "cgeMotionFlowFilter.h"

namespace CGE
{
	CGEDynamicWaveFilter* createDynamicWaveFilter();
    CGEMotionFlowFilter* createMotionFlowFilter();
}

#endif