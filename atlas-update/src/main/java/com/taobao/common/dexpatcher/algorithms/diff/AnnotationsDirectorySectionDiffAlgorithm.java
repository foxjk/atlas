/*
 * Tencent is pleased to support the open source community by making Tinker available.
 *
 * Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taobao.common.dexpatcher.algorithms.diff;


import com.taobao.dex.AnnotationsDirectory;
import com.taobao.dex.Dex;
import com.taobao.dex.TableOfContents;
import com.taobao.dex.io.DexDataBuffer;
import com.taobao.dx.util.IndexMap;

/**
 * Created by tangyinsheng on 2016/6/30.
 */
public class AnnotationsDirectorySectionDiffAlgorithm extends DexSectionDiffAlgorithm<AnnotationsDirectory> {
    public AnnotationsDirectorySectionDiffAlgorithm(Dex oldDex, Dex newDex, IndexMap oldToNewIndexMap, IndexMap oldToPatchedIndexMap, IndexMap newToPatchedIndexMap, IndexMap selfIndexMapForSkip) {
        super(oldDex, newDex, oldToNewIndexMap, oldToPatchedIndexMap, newToPatchedIndexMap, selfIndexMapForSkip);
    }

    @Override
    protected TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().annotationsDirectories;
    }

    @Override
    protected AnnotationsDirectory nextItem(DexDataBuffer section) {
        return section.readAnnotationsDirectory();
    }

    @Override
    protected int getItemSize(AnnotationsDirectory item) {
        return item.byteCountInDex();
    }

    @Override
    protected AnnotationsDirectory adjustItem(IndexMap indexMap, AnnotationsDirectory item) {
        return indexMap.adjust(item);
    }

    @Override
    protected void updateIndexOrOffset(IndexMap indexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldOffset != newOffset) {
            indexMap.mapAnnotationsDirectoryOffset(oldOffset, newOffset);
        }
    }

    @Override
    protected void markDeletedIndexOrOffset(IndexMap indexMap, int deletedIndex, int deletedOffset) {
        indexMap.markAnnotationsDirectoryDeleted(deletedOffset);
    }
}
